package org.qm.common.dao.dataDao;

import org.qm.domain.data.DataTableBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;

@NoRepositoryBean
public interface BaseDao<T,ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    List<DataTableBase> findByTimeAfter(Date time);
    List<DataTableBase> findAllByIdIn(List<String> ids);
}
