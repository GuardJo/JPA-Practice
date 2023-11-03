package com.practice.jpa.chapter10;

import java.util.List;
import java.util.stream.Collectors;

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
import com.practice.jpa.chapter10.dto.UserDto10;

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
		selectParameterBindingQuery();
		findTeam();
		findAddress();
		calcScalarData();
		findMultiData();
		createNewDto();
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

	private void selectParameterBindingQuery() {
		System.out.println("Parameter Binding 종류 별 조회 테스트");
		String searchName = "Tester";
		List<Member10_2> members;

		System.out.println("이름 기준");
		TypedQuery<Member10_2> nameQuery = entityManager.createQuery("select m from Member10_2 m where m.username = :searchName", Member10_2.class);
		members = nameQuery
			.setParameter("searchName", searchName)
			.getResultList();

		members.forEach(System.out::println);

		System.out.println("위치 기준");
		TypedQuery<Member10_2> locationQuery = entityManager.createQuery("select m from Member10_2 m where m.username = ?1", Member10_2.class);
		members = locationQuery
			.setParameter(1, searchName)
			.getResultList();

		members.forEach(System.out::println);
	}

	private void findTeam() {
		System.out.println("Entity Projection 테스트");
		String username = "Tester";

		TypedQuery<Team10> query = entityManager.createQuery("select m.team from Member10_2 m where m.username = :name", Team10.class);
		List<Team10> teams = query
			.setParameter("name", username)
			.getResultList();

		teams.forEach(System.out::println);
	}

	private void findAddress() {
		System.out.println("Embedded Type Projection 테스트");

		TypedQuery<Address10> query = entityManager.createQuery("select o.address from Order10 o", Address10.class);
		List<Address10> addresses = query.getResultList();

		addresses.forEach(System.out::println);
	}

	private void calcScalarData() {
		System.out.println("Scalar Type Projection 테스트");

		TypedQuery<Member10_2> distinctQuery = entityManager.createQuery("select distinct m from Member10_2 m", Member10_2.class);
		List<Member10_2> members = distinctQuery.getResultList();
		members.forEach(System.out::println);

		TypedQuery<Double> avgQuery = entityManager.createQuery("select avg(o.orderAmount) from Order10 o", Double.class);
		double average = avgQuery.getSingleResult();
		System.out.println("OrderAmount Avg = " + average);
	}

	private void findMultiData() {
		System.out.println("Multi Data Projection 테스트");

		// 여러 필드값 조회
		Query nameAndAgeQuery = entityManager.createQuery("select m.username, m.age from Member10_2 m");
		List<Object[]> objects = nameAndAgeQuery.getResultList();

		objects.forEach((obs) -> {
			String name = (String)obs[0];
			int age = (int)obs[1];

			System.out.printf("name = %s, age = %d\n", name, age);
		});

		// 여러 엔티티 조회
		Query memberAndProductQuery = entityManager.createQuery("select o.member, o.product from Order10 o");
		List<Object[]> objects1 = memberAndProductQuery.getResultList();

		objects1.forEach((obs) -> {
			Member10_2 member = (Member10_2)obs[0];
			Product10 product = (Product10)obs[1];

			System.out.printf("member = %s, product = %s\n", member, product);
		});
	}

	private void createNewDto() {
		System.out.println("데이터 조회 시 NEW 명령어 처리 테스트");

		System.out.println("- NEW 명령어 수행 X");
		Query notUseNewQuery = entityManager.createQuery("select m.username, m.age from Member10_2 m");
		List<Object[]> objects = notUseNewQuery.getResultList();
		List<UserDto10> userDtos = objects.stream()
			.map((obj) -> {
				String username = (String)obj[0];
				int age = (int)obj[1];

				return new UserDto10(username, age);
			})
			.collect(Collectors.toList());
		userDtos.forEach(System.out::println);

		System.out.println("- NEW 명령어 수행 O");
		TypedQuery<UserDto10> userDtoQuery = entityManager.createQuery(
			"select new com.practice.jpa.chapter10.dto.UserDto10(m.username, m.age) from Member10_2 m", UserDto10.class);
		List<UserDto10> userDtoList = userDtoQuery.getResultList();
		userDtoList.forEach(System.out::println);
	}
}
