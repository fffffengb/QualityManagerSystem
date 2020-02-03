package org.qm.data.avgData.service;

import org.qm.common.dao.dataDao.stat.StatAvgDao;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.data.stat.DStatAvg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatAvgService {

    private StatAvgDao statAvgDao;
    private QueryUtils queryUtils;


    @Autowired
    public StatAvgService(StatAvgDao StatAvgDao, QueryUtils queryUtils) {
        this.statAvgDao = StatAvgDao;
        this.queryUtils = queryUtils;

    }

    //根据指定statId查询当前用户管理下工位数据
    public Page<DStatAvg> findAllByStatId(String statId, int page, int size) {
        return statAvgDao.findAllByStatId(statId, PageRequest.of(page - 1, size));
    }
    //查询所有当前登录用户管理的工位的数据
    public Page<DStatAvg> findAllManaged(int page, int size) {
        //根据用户信息查询需要的结果并返回
        List<String> allManagedStat = queryUtils.getAllManaged("stat");
        return statAvgDao.findAllByStatIdIn(allManagedStat, PageRequest.of(page - 1, size));
    }

    public boolean hasPermFind(String statId) {
        return queryUtils.hasPermFind(statId, "stat");
    }

}
