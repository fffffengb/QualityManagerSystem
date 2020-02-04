package org.qm.sys.service;


import org.qm.common.dao.configDao.ConfBaseDao;
import org.qm.common.dao.configDao.ConfUserDao;
import org.qm.common.exception.NoSuchIdException;
import org.qm.common.utils.IdWorker;
import org.qm.domain.conf.ConfBaseInquire;
import org.qm.domain.conf.ConfUserInquire;
import org.qm.domain.system.Role;
import org.qm.domain.system.User;
import org.qm.sys.dao.RoleDao;
import org.qm.sys.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private IdWorker idWorker;
    private RoleDao roleDao;
    private ConfBaseDao confBaseDao;
    private ConfUserDao confUserDao;

    @Autowired
    public UserService(UserDao userDao, IdWorker idWorker, RoleDao roleDao, ConfBaseDao confBaseDao, ConfUserDao confUserDao) {
        this.userDao = userDao;
        this.idWorker = idWorker;
        this.roleDao = roleDao;
        this.confBaseDao = confBaseDao;
        this.confUserDao = confUserDao;
    }

    /**
     * 1.保存用户
     */
    public void save(User user) {
        //设置主键的值
        String id = idWorker.nextId()+"";
        user.setId(id);
        //初始化用户设置信息
        initUserConf(id);
        //调用dao保存部门
        userDao.save(user);
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
    public User findById(String id) {
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

    public Page<User> findAll(Map<String,Object> map,int page, int size) {
        //1.需要查询条件
        Specification<User> spec = new Specification<User>() {
            /**
             * 动态拼接查询条件
             */
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if(!StringUtils.isEmpty(map.get("username"))) {
                    list.add(criteriaBuilder.equal(root.get("username").as(String.class),map.get("username")));
                }
                int size = list.size();
                return criteriaBuilder.and(list.toArray(new Predicate[size]));
            }
        };

        //2.分页
        return userDao.findAll(spec, PageRequest.of(page-1, size));
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    /**
     * 5.根据id删除用户
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    public void assignRole(Map<String, Object> map) {
        //1.根据Map中的用户id查找要被分配的用户
        User user;
        Role role;
        String id = (String) map.get("id");
        if (!userDao.findById(id).isPresent()) throw new NoSuchIdException("没有查找到此用户：" + id);
        user = userDao.findById(id).get();
        //2.构造Role集合
        List<String> roleIds = (List<String>) map.get("roleIds");
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
    }

    private void initUserConf(String userId) {
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
}
