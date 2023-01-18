package com.practice.jpa.chapter02.example;

import com.practice.jpa.data.Member;
import com.practice.jpa.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class JpaExample implements Runnable {
    private final EntityManager entityManager;

    public JpaExample(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void run() {
        System.out.println("Chapter02 Example Run!");

        saveTest(entityManager);
        updateTest(entityManager);
        findAllTest(entityManager);
        findAndRemoveTest(entityManager);

        entityManager.close();
    }
    private static void saveTest(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            MemberRepository memberRepository = new MemberRepository(entityManager);
            memberRepository.save(Member.of("1", "testName", 28));
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
    }

    private static void findAndRemoveTest(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            MemberRepository memberRepository = new MemberRepository(entityManager);
            Member member = memberRepository.findById("1");
            memberRepository.remove(member);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
    }

    private static void updateTest(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            MemberRepository memberRepository = new MemberRepository(entityManager);
            memberRepository.updateAge("1", 30);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
    }

    private static void findAllTest(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            MemberRepository memberRepository = new MemberRepository(entityManager);
            List<Member> members = memberRepository.findAll();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
    }
}
