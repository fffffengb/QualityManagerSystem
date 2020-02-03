package org.qm.common.dao.dataDao.group;

import org.qm.common.dao.dataDao.BaseDao;
import org.qm.domain.data.group.DGroupAvg;

import java.util.List;

public interface GroupAvgDao extends BaseDao<DGroupAvg, String> {
    DGroupAvg findAllByGroupIdIn(List<String> ids);
}
