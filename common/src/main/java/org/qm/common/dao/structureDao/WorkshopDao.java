package org.qm.common.dao.structureDao;

import org.qm.domain.base.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkshopDao extends JpaRepository<Workshop, String>, JpaSpecificationExecutor<Workshop> {
    Workshop findByEmployeeId(String employeeId);
}
