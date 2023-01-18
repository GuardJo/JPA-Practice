package com.practice.jpa.chapter03.example;

import com.practice.jpa.data.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Persistence Context에서 관리하는 Entity 객체에 대한 생명주기
 * New/Trasient : 비영속 상태, Persistence Context에 올라가지 않은 상태 (한번도)
 * Managed : 영속 상태, Persistence Context에 올라간 상태
 * Detached : 준영속 상태, Persistence Context에 올라갔다가 내려온 상태
 * Removed : 삭제 상태, Persistence Context에서 삭제된 상태
 */
public class EntityLifecycleExample implements Runnable{
    private final EntityManagerFactory entityManagerFactory;

    public EntityLifecycleExample(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run() {
        System.out.println("Chapter03 Example Run!");

        // 비영속 상태에서의 조회
        newAndTrasientState();

        // 영속 상태에서의 조회
        managedState();

        // 준영속 상태에서의 조회
        detachedState();

        // 삭제 상태에서의 조회
        removedState();
    }

    /**
     * Entity 비영속 조회
     */
    private void newAndTrasientState() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Member member = Member.of("new", "test", 10);

        System.out.println("비영속 상태 Entity 조회");

        try {
            transaction.begin();
//            entityManager.persist(member);
            Member findMember = entityManager.find(Member.class, member.getId());
            System.out.println("비영속 상태 조회 결과 : " + ((findMember == null) ? "null" : findMember.toString()));
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    /**
     * 영속 상태
     */
    private void managedState() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Member member = Member.of("managed", "test", 10);

        System.out.println("영속 상태 Entity 조회");

        try {
            transaction.begin();
            entityManager.persist(member);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            Member findMember = entityManager.find(Member.class, member.getId());
            System.out.println("영속 상태 조회 : " + member.toString());
            entityManager.close();
        }
    }

    /**
     * 해당하는 Entity 객체를 준영속화 상태로 변경한다.
     */
    private void detachedState() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Member member = Member.of("detached", "test", 10);

        System.out.println("준영속 상태 Entity 조회");

        try {
            transaction.begin();
            entityManager.detach(member); // <- Persistence Context 내 해당 Entity 제거 (저장된 해당 Entity 준영속화)
//            entityManager.clear(); <- Persistence Context를 비움 (저장된 모든 Entity 준영속화)
//            entityManager.close(); <- Persistence Context를 닫음 (저장된 모든 Entity 준영속화)
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            Member findMember = entityManager.find(Member.class, member.getId());
            System.out.println("준영속 상태 조회 : " + (findMember == null ? "null" : findMember.toString()));
            entityManager.close();
        }
    }

    /**
     * 해당하는 Entity 객체를 비영속화 상태로 변경한다.
     */
    private void removedState() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        System.out.println("삭제 상태 Entity 조회");

        try {
            transaction.begin();
            Member member = entityManager.find(Member.class, "managed");
            entityManager.remove(member);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        } finally {
            Member findMember = entityManager.find(Member.class, "managed");
            System.out.println("삭제 상태 조회 : " + ((findMember == null) ? "null" : findMember.toString()));
            entityManager.close();
        }
    }
}
