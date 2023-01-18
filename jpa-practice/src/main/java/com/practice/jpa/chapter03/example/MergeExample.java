package com.practice.jpa.chapter03.example;

import com.practice.jpa.data.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class MergeExample implements Runnable{
    private final EntityManagerFactory entityManagerFactory;

    public MergeExample(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run() {
        Member member = createMember("mergeTest", "test");

        member.setAge(10); // 준영속 상태에서의 변경 (이 시점에는 DB에는 변경요소가 반영이 안됨)

        mergeMember(member);

        clearData(member.getId());
    }

    /**
     * 특정 Entity 객체를 저장하나, 메소드 내에서 close()되어 준영속 상태의 entity 객체를 반환함
     * @param id member id
     * @param name member userName
     * @return 준영속화된 Entity 객체
     */
    private Member createMember(String id, String name) {
        Member member = Member.of(id, name, 300);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(member);
        transaction.commit();

        entityManager.close();

        return member;
    }

    /**
     * 인자로 주어진 Entity객체를 병합하여 Persistence Context의 관리하에 둔다
     * @param member 병합할 준영속 Entity객체
     */
    private void mergeMember(Member member) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Member mergeMember = entityManager.merge(member);
        transaction.commit();

        System.out.println("준영속화 상태  : " + member.toString());
        System.out.println("병합된 준영속 상태 : " + mergeMember.toString());
        System.out.println("준영속 상태의 관리 여부 : " + entityManager.contains(member));
        System.out.println("병합된 준영속 상태의 관리 여부 : " + entityManager.contains(mergeMember));

        entityManager.close();
    }

    /**
     * 테스트를 위해 만들었던 Entity 객체 정리
     * @param id 테스트했던 Entity 객체 식별값
     */
    private void clearData(String id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Member member = entityManager.find(Member.class, id);
        entityManager.remove(member);
        transaction.commit();
        entityManager.close();
    }
}
