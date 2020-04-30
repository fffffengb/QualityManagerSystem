package org.qm.common.service;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.qm.common.dao.MemberDao;
import org.qm.common.dao.configDao.ConfBaseDao;
import org.qm.common.dao.configDao.ConfUserDao;
import org.qm.common.entity.UserResult;
import org.qm.common.exception.NoSuchIdException;
import org.qm.common.utils.IdWorker;
import org.qm.domain.base.Member;
import org.qm.domain.conf.ConfBaseInquire;
import org.qm.domain.conf.ConfUserInquire;
import org.qm.domain.system.Role;
import org.qm.domain.system.User;
import org.qm.common.dao.RoleDao;
import org.qm.common.dao.UserDao;
import org.qm.common.shior.JwtUtils;
import org.qm.common.shior.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService {
    private UserDao userDao;
    private MemberDao memberDao;
    private RoleDao roleDao;
    private ConfBaseDao confBaseDao;
    private ConfUserDao confUserDao;
    private IdWorker idWorker;

    @Autowired
    public UserService(UserDao userDao, MemberDao memberDao, RoleDao roleDao, ConfBaseDao confBaseDao, ConfUserDao confUserDao, IdWorker idWorker) {
        this.userDao = userDao;
        this.memberDao = memberDao;
        this.roleDao = roleDao;
        this.confBaseDao = confBaseDao;
        this.confUserDao = confUserDao;
        this.idWorker = idWorker;
    }

    /**
     * 登录
     */
    public Map<String, String> login(String username, String password) {
        User curUser = userDao.findByUsername(username);
        if (curUser == null)
            throw new UnknownAccountException("该用户不存在");
        Map<String, Object> principal = new HashMap<>();
        principal.put("userId", curUser.getId());
        principal.put("username", curUser.getUsername());
        ShiroUtils.setCurUser(curUser);
        ShiroUtils.setPrincipal(principal);
        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, encode(password, curUser.getSalt())));
        // 登陆成功返回昵称和jwtToken, 失败抛出异常统一处理
        Map<String, String> res = new HashMap<>();
        res.put("username", curUser.getUsername());
        res.put("token", JwtUtils.getToken(curUser.getId(), curUser.getUsername()));
        return res;
    }

    /**
     * 注册
     */
    public Map<String, Object> register(int employeeId, String username, List<String> roleIds, String password) {
        // 检查公司是否有此员工
        if(!memberDao.findById(employeeId).isPresent())
            throw new NoSuchIdException("公司没有此员工");
        User newUser = new User();
        newUser.setId(employeeId);
        newUser.setUsername(username);
        // 构造一个新的盐用于加密密码
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        newUser.setPassword(encode(password, salt));
        newUser.setSalt(salt);
        newUser.setIs_superuser(0);
        userDao.save(newUser);
        newUser = assignRole(employeeId, roleIds);
        Map<String, Object> res = new HashMap<>();
        res.put("username", username);
        res.put("roles", newUser.getRoles());
        return res;
    }

    private String encode(String password, String salt) {
        return new SimpleHash("md5", password, salt, 2).toString();
    }


    /**
     * 2.更新用户
     */
    public void update(User user) {
        //1.根据id查询部门
        User target = userDao.findById(user.getId()).get();
        //2.设置部门属性
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setIs_superuser(user.getIs_superuser());
        //3.更新部门
        userDao.save(target);
    }

    /**
     * 3.根据id查询用户
     */
    public User findById(int id) {
        if (userDao.findById(id).isPresent())
            return userDao.findById(id).get();
        else
            return null;
    }

    /**
     * 4.查询全部用户列表
     *      参数：map集合的形式
     *          hasDept
     *          departmentId
     *          companyId
     *
     */

    public Page<UserResult> findAll(Integer userId, String username, int page, int size) {
        //构造查询条件
        Specification<User> spec = (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), userId));
            }
            if (username != null) {
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        //获得用户数据
        List<User> users = userDao.findAll(spec);
        //构造需要的数据
        List<UserResult> userResults = getUserResult(users);
        //分页
        return listConvertToPage(userResults, PageRequest.of(page-1, size));
    }

    private List<UserResult> getUserResult(List<User> users) {
        //根据用户id查找员工信息
        List<Integer> ids = new ArrayList<>();
        List<UserResult> results = new ArrayList<>();
        Map<String, String> roleMap = getRoleMap();
        //构造结果集
        for (User user : users) {
            UserResult userResult = new UserResult(user);
            Member member = memberDao.findById(user.getId()).get();
            userResult.setName(member.getName());
            Set<Role> roleSet = user.getRoles();
            List<String> roleNames = new ArrayList<>();
            for (Role role : roleSet) {
                roleNames.add(roleMap.get(role.getName()));
            }
            userResult.setRoleNames(roleNames);
            results.add(userResult);
        }
        return results;
    }

    private Map<String, String> getRoleMap() {
        Map<String, String> map = new HashMap<>();
        map.put("group", "大组长");
        map.put("workshop", "经理");
        map.put("dpt", "总经理");
        map.put("hr", "人事经理");
        return map;
    }

    public <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    /**
     * 5.根据id删除用户
     */
    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    public User assignRole(int id, List<String> roleIds) {
        //1.根据Map中的用户id查找要被分配的用户
        User user;
        Role role;
        if (!userDao.findById(id).isPresent()) throw new NoSuchIdException("没有查找到此用户：" + id);
        user = userDao.findById(id).get();
        //2.构造Role集合
        Set<Role> roleSet = new HashSet<>();
        for (String roleId : roleIds) {
            if (!roleDao.findById(roleId).isPresent()) throw new NoSuchIdException("没有查找到此角色：" + roleId);
            role = roleDao.findById(roleId).get();
            roleSet.add(role);
        }
        //3.设置角色
        user.setRoles(roleSet);
        //4.保存用户信息
        userDao.save(user);
        return user;
    }

    private void initUserConf(int userId) {
        //1.查找所有基本设置
        List<ConfBaseInquire> allBaseConfList = confBaseDao.findAll();
        //2.初始化ConfUserInquire对象
        List<ConfUserInquire> allUserConfList = new ArrayList<>();
        for (ConfBaseInquire b : allBaseConfList) {
            String id = idWorker.nextId() + "";
            String value = b.getDefaultValue();
            String conf_id = b.getId();
            allUserConfList.add(new ConfUserInquire(id, value, conf_id, userId));
        }
        //3.添加所有基本设置
        confUserDao.saveAll(allUserConfList);
    }

    public Integer deleteByUsername(String username) {
        return userDao.deleteByUsername(username);
    }
}
