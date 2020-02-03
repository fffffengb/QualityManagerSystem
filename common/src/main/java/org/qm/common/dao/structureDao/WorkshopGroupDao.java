package org.qm.common.dao.structureDao;

import org.qm.domain.base.WorkshopGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkshopGroupDao extends JpaRepository<WorkshopGroup, String>, JpaSpecificationExecutor<WorkshopGroup> {
    List<WorkshopGroup> findAllByWorkshopIdIn(List<String> allIds);
    List<WorkshopGroup> findAllByWorkshopId(String workshopId);
    @Query(value = "SELECT groupId FROM WorkshopGroup WHERE workshopId = :workshopId")
    List<String> findAllGroupIdByWorkshopId(@Param("workshopId") String workshopId);
}
