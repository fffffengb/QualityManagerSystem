package org.qm.data.onlineData.service;

import org.apache.shiro.authz.UnauthorizedException;
import org.qm.common.dao.dataDao.stat.StatOnlineDao;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.data.stat.DStatOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    // 根据工位id和提供的时间动态构造查询条件进行查询
    public Page<DStatOnline> findAllByStatIdAndTime(String statId, Date start, Date end, String ascOrder, int page, int size) {

        System.out.println(start + " " + end);
        return statOnlineDao.findAll((Specification<DStatOnline>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            criteriaQuery.distinct(true);
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(ascOrder)));
            if (statId != null) {  // 指定statId
                if (!hasPermFind(statId))
                    throw new UnauthorizedException("当前用户没有查询该id的权限");
                predicates.add(criteriaBuilder.equal(root.get("statId"), statId));
            } else {  // 未指定则查询当前用户管理下所有工位信息
                List<String> allManagedStat = queryUtils.getAllManaged("stat");
                predicates.add(root.get("statId").in(allManagedStat));
            }
            if (start!= null && end != null) {
                predicates.add(criteriaBuilder.between(root.get("time"), start, end));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, PageRequest.of(page - 1, size));
    }

    public boolean hasPermFind(String statId) {
        return queryUtils.hasPermFind(statId, "stat");
    }
}
