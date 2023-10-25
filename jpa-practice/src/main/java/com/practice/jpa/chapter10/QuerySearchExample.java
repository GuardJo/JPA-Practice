package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.practice.jpa.chapter10.domain.Member10;

public class QuerySearchExample implements Runnable {
	private final EntityManager entityManager;

	public QuerySearchExample(EntityManagerFactory entityManagerFactory) {
		this(entityManagerFactory.createEntityManager());
	}

	public QuerySearchExample(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void run() {
		initData();
		searchByJpql();
	}

	private void initData() {
		Member10 member1 = Member10.create("test1");
		Member10 member2 = Member10.create("test2");

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(member1);
		entityManager.persist(member2);
		transaction.commit();
	}

	private void searchByJpql() {
		String searchName = "test2";
		System.out.println("JPQL 검색 쿼리 테스트 : name = " + searchName);

		String jpql = "select m from Member10 m where m.name = '" + searchName + "'";

		List<Member10> members = entityManager.createQuery(jpql, Member10.class).getResultList();

		members.forEach(System.out::println);
	}
}
