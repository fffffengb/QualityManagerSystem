package org.qm.common.dao.structureDao;

import org.qm.domain.base.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupDao extends JpaRepository<Group, String>, JpaSpecificationExecutor<Group> {
    Group findByEmployeeId(int employeeId);
}
