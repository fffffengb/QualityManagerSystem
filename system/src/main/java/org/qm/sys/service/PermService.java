package org.qm.sys.service;

import org.qm.common.exception.NoSuchIdException;
import org.qm.domain.system.Permission;
import org.qm.sys.dao.PermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermService {
    private PermDao permDao;

    @Autowired
    public PermService(PermDao permDao) {
        this.permDao = permDao;
    }

    public void save(Permission permission) {
        permDao.save(permission);
    }

    public List<Permission> findAll() {
        return permDao.findAll();
    }

    public Permission update(Permission permission) {
        Permission target = findById(permission.getId());
        target.setName(permission.getName());
        permDao.save(target);
        return target;
    }

    public Permission delete(String id) {
        Permission target = findById(id);
        permDao.delete(target);
        return target;
    }

    //根据id查找，没找到就向前端返回提示信息。异常在common包统一处理。
    public Permission findById(String id) {
        Permission target;
        if (!(permDao.findById(id).isPresent())) throw new NoSuchIdException("没有这个权限Id：" + id);
        target = permDao.findById(id).get();
        return target;
    }

}
