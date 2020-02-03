package org.qm.common.dao.dataDao.stat;

import org.qm.common.dao.dataDao.BaseDao;
import org.qm.domain.data.stat.DStatOnline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatOnlineDao extends BaseStatDao<DStatOnline, String>{
}
