package org.qm.common.dao.structureDao;

import org.qm.domain.base.GroupStatMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupStatMemberDao extends JpaRepository<GroupStatMember, String>, JpaSpecificationExecutor<GroupStatMember> {
    @Query(value = "SELECT statId FROM GroupStatMember WHERE groupId = :groupId")
    List<String> findAllStatIdByGroupId(String groupId);
    @Query(value = "SELECT statId FROM GroupStatMember WHERE groupId in :groupIds")
    List<String> findAllStatIdByGroupIdIn(List<String> groupIds);
}
