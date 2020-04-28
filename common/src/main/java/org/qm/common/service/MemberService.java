package org.qm.common.service;

import org.qm.common.utils.IdWorker;
import org.qm.domain.base.Member;
import org.qm.common.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    private MemberDao memberDao;
    private IdWorker idWorker;

    @Autowired
    public MemberService(MemberDao memberDao, IdWorker idWorker) {
        this.memberDao = memberDao;
        this.idWorker = idWorker;
    }

    public Member save(Member member) {
        memberDao.save(member);
        return member;
    }

    public Page<Member> findAll(Map<String, Object> map, int page, int size) {
        //1.需要查询条件
        Specification<Member> spec = new Specification<Member>() {
            /**
             * 动态拼接查询条件
             */
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if(!StringUtils.isEmpty(map.get("id"))) {
                    list.add(criteriaBuilder.equal(root.get("id").as(String.class), map.get("id")));
                }
                if(!StringUtils.isEmpty(map.get("name"))) {
                    list.add(criteriaBuilder.equal(root.get("name").as(String.class), map.get("name")));
                }
                if(!StringUtils.isEmpty(map.get("sex"))) {
                    list.add(criteriaBuilder.equal(root.get("sex").as(String.class), map.get("sex")));
                }
                if(!StringUtils.isEmpty(map.get("birthday"))) {
                    list.add(criteriaBuilder.equal(root.get("birthday").as(String.class), map.get("birthday")));
                }
                if(!StringUtils.isEmpty(map.get("birthplace"))) {
                    list.add(criteriaBuilder.equal(root.get("birthplace").as(String.class), map.get("birthplace")));
                }
                int size = list.size();
                return criteriaBuilder.and(list.toArray(new Predicate[size]));
            }
        };

        //2.分页
        return memberDao.findAll(spec, PageRequest.of(page-1, size));
    }

    public Member findById(int id) {
        if (memberDao.findById(id).isPresent())
            return memberDao.findById(id).get();
        return null;
    }

    public void update(Member member) {
        memberDao.save(member);
    }

    public void deleteById(int id) {
        memberDao.deleteById(id);
    }
}
