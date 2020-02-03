package org.qm.sys.service;

import org.qm.common.exception.NoSuchIdException;
import org.qm.domain.system.Permission;
import org.qm.domain.system.Role;
import org.qm.sys.dao.PermDao;
import org.qm.sys.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService {
    private RoleDao roleDao;
    private PermDao permDao;

    @Autowired
    public RoleService(RoleDao roleDao, PermDao permDao) {
        this.roleDao = roleDao;
        this.permDao = permDao;
    }

    public void save(Role role) {
        roleDao.save(role);
    }

    public List<String> findAll() {
        List<String> res = new ArrayList<>();
        List<Role> allRole = roleDao.findAll();
        for (Role role : allRole) {
            res.add(role.getName());
        }
        return res;
    }

    public void assignPerms(Map<String, Object> map) {
        Role role;
        //1.查找要被分配权限的角色
        if (!roleDao.findById((String)map.get("id")).isPresent())
            //2.没找到交给Spring统一处理异常
            throw new NoSuchIdException("没有找到该角色：" + map.get("id"));
        else{
            role = roleDao.findById((String)map.get("id")).get();
            //3.将权限列表拿出来
            List<String> perms = (List<String>) map.get("permIds");
            Set<Permission> permissionSet = new HashSet<>();
            for (String permId : perms) {
                if (!permDao.findById(permId).isPresent()) throw new NoSuchIdException("没有找到该权限：" + permId);
                Permission curPerm = permDao.findById(permId).get();
                permissionSet.add(curPerm);
            }
            //4.设置权限
            role.setPerms(permissionSet);
            //5.保存角色
            roleDao.save(role);
        }

    }
}
