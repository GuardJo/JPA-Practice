package com.practice.jpa.chapter03;

import com.practice.jpa.data.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Persistence Context의 Dirty Checking (변경 감지) Example
 */
public class DirtyCheckingExample implements Runnable{
    private final EntityManagerFactory entityManagerFactory;

    public DirtyCheckingExample(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run() {
        System.out.println("Persistence Context 변경 감지 테스트");
        dirtyChecking();
    }
    
    private void dirtyChecking() {
        Member member = Member.of("test", "test", 100);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(member);
            Member findMember = entityManager.find(Member.class, member.getId());
            System.out.println("수정 전 Entity : " + findMember.toString());

            findMember.setAge(30);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            Member updateMember = entityManager.find(Member.class, member.getId());
            System.out.println("수정 후 Entity : " + updateMember.toString());
            entityManager.close();
        }
    }
}
