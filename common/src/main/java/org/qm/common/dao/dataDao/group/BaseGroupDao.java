package org.qm.common.dao.dataDao.group;

import org.qm.common.dao.dataDao.BaseDao;
import org.qm.domain.data.group.DGroupOnline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseGroupDao<T, ID> extends BaseDao<T, ID> {
    Page<T> findAllByGroupIdIn(List<String> ids, Pageable pageable);
    Page<T> findAllByGroupId(String groupId, Pageable pageable);
}
