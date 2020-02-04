package org.qm.common.dao.configDao;

import org.qm.domain.conf.ConfBaseInquire;
import org.qm.domain.conf.ConfUserInquire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConfBaseDao extends JpaRepository<ConfBaseInquire, String>, JpaSpecificationExecutor<ConfBaseInquire> {
}
