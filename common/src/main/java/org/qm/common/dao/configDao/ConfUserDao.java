package org.qm.common.dao.configDao;

import org.qm.domain.conf.ConfUserInquire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ConfUserDao extends JpaRepository<ConfUserInquire, String>, JpaSpecificationExecutor<ConfUserInquire> {
    List<ConfUserInquire> findAllByEmployeeId(String employeeId);
    ConfUserInquire findByConfIdAndEmployeeId(String id, String employeeId);
}