package org.qm.common.dao.dataDao.group;

import org.qm.common.dao.dataDao.BaseDao;
import org.qm.domain.data.group.DGroupOnline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupOnlineDao extends BaseDao<DGroupOnline, String> {
    Page<DGroupOnline> findAllByGroupIdIn(List<String> ids, Pageable pageable);
    Page<DGroupOnline> findAllByGroupId(String groupId, Pageable pageable);
}
