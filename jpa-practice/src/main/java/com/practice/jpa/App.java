package com.practice.jpa;

import com.practice.jpa.data.Member;
import com.practice.jpa.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Hello JPA!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello JPA!");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        saveTest(entityManager);
        updateTest(entityManager);
        findAllTest(entityManager);
        findAndRemoveTest(entityManager);

        entityManager.close();
        entityManagerFactory.close();
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
