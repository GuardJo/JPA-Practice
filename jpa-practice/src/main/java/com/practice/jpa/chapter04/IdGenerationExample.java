package com.practice.jpa.chapter04;

import com.practice.jpa.data.Board;
import com.practice.jpa.data.Board2;
import com.practice.jpa.data.Board3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Date;

public class IdGenerationExample implements Runnable{
    private final EntityManagerFactory entityManagerFactory;

    public IdGenerationExample(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run() {
        Board board = Board.of("title", "test", "tester", new Date());
        Board2 board2 = Board2.of("title", "test", "tester");
        Board3 board3 = Board3.of("title", "test", "tester");

        persistBoardWithoutId(board);
        persistBoard2WithoutId(board2);
        persistBoard3WithoutId(board3);
    }

    /**
     * IDENTITY 전략 테스트
     * @param board IDENTITY 전략을 사용한 Entity 객체
     */
    private void persistBoardWithoutId(Board board) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(board);
        System.out.println("DB 할당으로 부여된 ID : " + board.getId());
        transaction.commit();
        entityManager.close();
    }

    /**
     * SEQUENCE 전략 테스트
     * @param board2 SEQUENCE 전략을 사용한 Entity 객체
     */
    private void persistBoard2WithoutId(Board2 board2) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(board2);
        System.out.println("SEQUNCE 전략으로 부여된 ID : " + board2.getId());
        transaction.commit();
        entityManager.close();
    }

    private void persistBoard3WithoutId(Board3 board3) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(board3);
        System.out.println("TABLE 전략으로 부여된 ID : " + board3.getId());
        transaction.commit();
        entityManager.close();
    }
}
