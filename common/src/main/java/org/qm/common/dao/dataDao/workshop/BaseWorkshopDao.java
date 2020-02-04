package org.qm.common.dao.dataDao.workshop;

import org.qm.common.dao.dataDao.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseWorkshopDao<T, ID> extends BaseDao<T, ID> {
    Page<T> findAllByWorkshopIdIn(List<String> ids, Pageable pageable);
    Page<T> findAllByWorkshopId(String statId, Pageable pageable);
}
