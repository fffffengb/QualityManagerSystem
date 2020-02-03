package org.qm.data.onlineData.service;

import org.qm.common.dao.dataDao.stat.StatOnlineDao;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.data.stat.DStatOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatOnlineService {

    private StatOnlineDao statOnlineDao;
    private QueryUtils queryUtils;


    @Autowired
    public StatOnlineService(StatOnlineDao statOnlineDao, QueryUtils queryUtils) {
        this.statOnlineDao = statOnlineDao;
        this.queryUtils = queryUtils;

    }

    //根据指定statId查询当前用户管理下工位数据
    public Page<DStatOnline> findAllByStatId(String statId, int page, int size) {
        return statOnlineDao.findAllByStatId(statId, PageRequest.of(page - 1, size));
    }
    //查询所有当前登录用户管理的工位的数据
    public Page<DStatOnline> findAllManaged(int page, int size) {
        //根据用户信息查询需要的结果并返回
        List<String> allManagedStat = queryUtils.getAllManaged("stat");
        return statOnlineDao.findAllByStatIdIn(allManagedStat, PageRequest.of(page - 1, size));
    }

    public boolean hasPermFind(String statId) {
        return queryUtils.hasPermFind(statId, "stat");
    }
}
