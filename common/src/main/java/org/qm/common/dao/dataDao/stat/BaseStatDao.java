package org.qm.common.dao.dataDao.stat;

import org.qm.common.dao.dataDao.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseStatDao<T, ID> extends BaseDao<T, ID> {
    Page<T> findAllByStatIdIn(List<String> ids, Pageable pageable);
    Page<T> findAllByStatId(String statId, Pageable pageable);
}
