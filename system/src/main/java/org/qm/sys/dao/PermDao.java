package org.qm.sys.dao;

import org.qm.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermDao extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {
    Permission findByName(String name);
}
