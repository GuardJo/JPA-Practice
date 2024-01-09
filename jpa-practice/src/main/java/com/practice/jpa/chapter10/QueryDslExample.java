package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.mysema.query.jpa.impl.JPAQuery;
import com.practice.jpa.chapter10.domain.Member10_2;
import com.practice.jpa.chapter10.domain.QMember10_2;

public class QueryDslExample implements Runnable {
	private final EntityManager entityManager;

	public QueryDslExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		searchAllMembers();
		searchByNameMembers();
	}

	private void searchAllMembers() {
		System.out.println("QueryDSL을 활용하여 전체 Member10_2 목록 반환");

		JPAQuery jpaQuery = new JPAQuery(entityManager);
		QMember10_2 member = QMember10_2.member10_2;

		List<Member10_2> members = jpaQuery.from(member)
			.list(member);

		members.forEach(System.out::println);
	}

	private void searchByNameMembers() {
		String name = "Tester";
		int age = 20;
		System.out.println(String.format("QueryDSL을 활용하여 Member10_2에서 username이 %s이고 age가 %d 이상인 요소 반환", name, age));

		JPAQuery jpaQuery = new JPAQuery(entityManager);
		QMember10_2 qMember = QMember10_2.member10_2;

		List<Member10_2> members = jpaQuery.from(qMember)
			.where(qMember.username.eq(name).and(qMember.age.gt(age)))
			.list(qMember);

		members.forEach(System.out::println);
	}
}
