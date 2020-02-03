package org.qm.sys.service;

import org.qm.domain.system.Permission;
import org.qm.sys.dao.PermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Permission update(Permission permission) {
        Permission target = null;
        if (permDao.findById(permission.getId()).isPresent()) {
            target = permDao.findById(permission.getId()).get();
            target.setName(permission.getName());
            permDao.save(target);
        }
        return target;
    }
}
