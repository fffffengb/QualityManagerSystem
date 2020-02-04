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

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public void assignPerms(Map<String, Object> map) {

        Role role = findById((String) map.get("id"));
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

    public Role delete(String id) {
        Role target = findById(id);
        roleDao.delete(target);
        return target;
    }

    public Role findById(String id) {
        //没找到交给Spring统一处理异常
        if (!roleDao.findById(id).isPresent()) throw new NoSuchIdException("没有找到该角色：" + id);
        return roleDao.findById(id).get();
    }
}
