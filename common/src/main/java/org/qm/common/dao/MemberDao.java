package org.qm.common.dao;

import org.qm.domain.base.Member;
import org.qm.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberDao extends JpaRepository<Member, Integer>, JpaSpecificationExecutor<Member> {
}
