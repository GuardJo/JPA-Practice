package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.practice.jpa.chapter10.domain.Address10;
import com.practice.jpa.chapter10.domain.Member10_2;
import com.practice.jpa.chapter10.domain.Order10;
import com.practice.jpa.chapter10.domain.Product10;
import com.practice.jpa.chapter10.domain.Team10;

public class JpqlSearchExample implements Runnable {
	private final EntityManager entityManager;

	public JpqlSearchExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		initData();
		selectData();
		selectDataWithTypeQuery();
		selectDataWithQuery();
	}

	private void initData() {
		Product10 product = Product10.create("New Product", 3000, 1);
		Team10 team = Team10.create("New Team");
		Member10_2 member = Member10_2.create("Tester", 28);
		member.setTeam(team);
		Address10 address = Address10.create("SEOUL", "GIL", "100-111");
		Order10 order = Order10.create(1, address, member, product);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(order);

		transaction.commit();
	}

	private void selectData() {
		System.out.println("JPQL SELECT문 테스트");
		String searchName = "Tester";

		String jpql = "select m from Member10_2 m where m.username = '" + searchName + "'";

		List<Member10_2> members = entityManager.createQuery(jpql, Member10_2.class).getResultList();

		members.forEach(System.out::println);
	}

	private void selectDataWithTypeQuery() {
		System.out.println("JPQL, TypeQuery 객체 조회 테스트");

		TypedQuery<Member10_2> typedQuery = entityManager.createQuery("select m from Member10_2 m where m.username = 'Tester'", Member10_2.class);
		List<Member10_2> members = typedQuery.getResultList();

		members.forEach(System.out::println);
	}

	private void selectDataWithQuery() {
		System.out.println("JPQL, Query 객체 조회 테스트");

		Query query = entityManager.createQuery("select m.username, m.age from Member10_2 m where m.username = 'Tester'");
		List objects = query.getResultList();

		objects.forEach((obj) -> {
			Object[] o = (Object[])obj;
			System.out.println(o[0]);
			System.out.println(o[1]);
		});
	}
}
