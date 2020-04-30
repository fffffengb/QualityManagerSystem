package org.qm.data.dailyData.service;

import org.apache.shiro.authz.UnauthorizedException;
import org.qm.common.dao.dataDao.stat.StatDailyDao;
import org.qm.common.dao.dataDao.workshop.WorkshopOnlineDao;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.data.stat.DStatDaily;
import org.qm.domain.data.stat.DStatOnline;
import org.qm.domain.data.workshop.DWorkshopOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatDailyService {
    private StatDailyDao statDailyDao;
    private QueryUtils queryUtils;

    @Autowired
    public StatDailyService(StatDailyDao statDailyDao, QueryUtils queryUtils) {
        this.statDailyDao = statDailyDao;
        this.queryUtils = queryUtils;
    }

    public Page<DStatDaily> findAllByStatIdAndTime(String statId, Date start, Date end, String ascOrder, int page, int size) {
        return statDailyDao.findAll((Specification<DStatDaily>) (root, criteriaQuery, criteriaBuilder) -> {
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
