package com.practice.jpa.repository;

import com.practice.jpa.data.Member;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MemberRepository {
    private final EntityManager entityManager;

    public MemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // 등록
    public void save(Member member) {
        entityManager.persist(member);
        System.out.println("save Member : " + member.toString());
    }

    // 수정
    public void updateAge(String id, int age) {
        Member member = entityManager.find(Member.class, id);
        int oldAge = member.getAge();
        member.setAge(age);

        System.out.println("Update Age, " + oldAge + " -> " + age);
    }

    // 조회
    public Member findById(String id) {
        Member member = entityManager.find(Member.class, id);

        System.out.println("Finded Member, " + member.toString());

        return member;
    }

    // 삭제
    public void remove(Member member) {
        entityManager.remove(member);

        System.out.println("Removed Member, " + member.toString());
    }

    // 검색
    public List<Member> findAll() {
        TypedQuery<Member> memberTypedQuery = entityManager.createQuery("select m from Member m", Member.class);
        List<Member> members = memberTypedQuery.getResultList();

        System.out.println("Find All Members (size : " + members.size() + ")");

        return members;
    }
}
