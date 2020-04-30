package org.qm.data.onlineData.service;

import org.apache.shiro.authz.UnauthorizedException;
import org.qm.common.dao.dataDao.workshop.WorkshopOnlineDao;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.data.workshop.DWorkshopOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkshopOnlineService {
    private WorkshopOnlineDao workshopOnlineDao;
    private QueryUtils queryUtils;

    @Autowired
    public WorkshopOnlineService(WorkshopOnlineDao workshopOnlineDao, QueryUtils queryUtils) {
        this.workshopOnlineDao = workshopOnlineDao;
        this.queryUtils = queryUtils;
    }
    // 根据工位id和提供的时间动态构造查询条件进行查询
    public Page<DWorkshopOnline> findAllByWorkshopIdAndTime(String workshopId, Date start, Date end, String ascOrder, int page, int size) {
        return workshopOnlineDao.findAll((Specification<DWorkshopOnline>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            criteriaQuery.distinct(true);
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(ascOrder)));
            if (workshopId != null) {
                if (!hasPermFind(workshopId))
                    throw new UnauthorizedException("当前用户没有查询该id的权限");
                predicates.add(criteriaBuilder.equal(root.get("workshopId"), workshopId));
            } else {  // 未指定则查询当前用户管理下所有信息
                List<String> allManagedStat = queryUtils.getAllManaged("workshop");
                predicates.add(root.get("workshopId").in(allManagedStat));
            }
            if (start!= null && end != null) {
                predicates.add(criteriaBuilder.between(root.get("time"), start, end));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, PageRequest.of(page - 1, size));
    }

    public boolean hasPermFind(String workshopId) {
        return queryUtils.hasPermFind(workshopId, "workshop");
    }

}
