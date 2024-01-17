package com.practice.jpa.chapter10;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.practice.jpa.chapter10.domain.Member10_2;

public class NativeQueryExample implements Runnable {
	private final EntityManager entityManager;

	public NativeQueryExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		findMembersByName();
		searchNamesInMembers();
		findByProductWithTotalCount();
	}

	private void findMembersByName() {
		System.out.println("Native Query를 활용한 Entity 조회");
		String username = "Tester";
		String sqlString = "SELECT * FROM CH10_MEMBER_2 m WHERE m.username = ?";

		Query query = entityManager.createNativeQuery(sqlString, Member10_2.class)
			.setParameter(1, username);

		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void searchNamesInMembers() {
		System.out.println("Native Query를 활용한 컬럼 값 조회");

		String sqlString = "SELECT m.username, m.age FROM CH10_MEMBER_2 m";

		Query query = entityManager.createNativeQuery(sqlString);

		List<Object[]> objects = query.getResultList();

		for (Object[] o : objects) {
			System.out.printf("username : %s, age : %d%n", o[0], (int)o[1]);
		}
	}

	private void findByProductWithTotalCount() {
		System.out.println("Native Query 및 결과 매핑을 활용하여 Product의 컬럼, 전체 수를 함께 조회");

		String sqlQuery = "SELECT p.id, p.name, p.price, p.stock_amount, (select count(p2.id) from CH10_PRODUCT p2) as total_product FROM CH10_PRODUCT p GROUP BY p.id";

		Query query = entityManager.createNativeQuery(sqlQuery, "entityWithTotalCount");

		List<Object[]> objects = query.getResultList();

		for (Object[] o : objects) {
			System.out.printf("%s, total = %d%n", o[0], (BigInteger)o[1]);
		}
	}
}
