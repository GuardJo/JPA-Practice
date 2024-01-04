package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.practice.jpa.chapter10.domain.Member10_2;
import com.practice.jpa.chapter10.domain.Team10;
import com.practice.jpa.chapter10.dto.UserDto10;

public class CriteriaExample implements Runnable {
	private final EntityManager entityManager;

	public CriteriaExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		searchAllMemberWithCriteria();
		searchAllObjectWithCriteria();
		searchMemberOnlyAdult();
		searchSelectExample();
		searchDistinctTeam();
		convertWithConstruct();
		searchTypeTuple();
		searchGroupByTeam();
		searchMemberOrderByAge();
	}

	private void searchAllMemberWithCriteria() {
		System.out.println("Criteria Builder 를 통한 Criteria Query 구성 및 조회");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery = criteriaQuery.select(m);

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();

		members.forEach(System.out::println);
	}

	private void searchAllObjectWithCriteria() {
		System.out.println("Criteria Builder 를 통한 Criteria Query 구성 및 Object 객체 조회");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(); // 반환 타입을 지정하지 않을 경우 Object 로 반환

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery.select(m);

		Query query = entityManager.createQuery(criteriaQuery);

		List<Object> objects = query.getResultList();

		objects.forEach(System.out::println);
	}

	private void searchMemberOnlyAdult() {
		System.out.println("Criteria를 통한 Member 조회 중 20세 이상인 Member들만 조회");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		Predicate predicate = criteriaBuilder.ge(m.get("age"), 20);
		Order order = criteriaBuilder.desc(m.get("age"));
		criteriaQuery
			.select(m)
			.where(predicate)
			.orderBy(order);

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void searchSelectExample() {
		System.out.println("조회대상 수에 따른 select문 처리");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaOneSelectQuery = criteriaBuilder.createQuery(Member10_2.class);
		CriteriaQuery<Object[]> criteriaMultiSelectQuery = criteriaBuilder.createQuery(Object[].class);

		Root<Member10_2> m = criteriaOneSelectQuery.from(Member10_2.class);
		Root<Member10_2> m2 = criteriaMultiSelectQuery.from(Member10_2.class);

		// 단일 조회 대상 처리
		criteriaOneSelectQuery.select(m);
		TypedQuery<Member10_2> query1 = entityManager.createQuery(criteriaOneSelectQuery);
		List<Member10_2> members = query1.getResultList();
		members.forEach(System.out::println);

		// 여러 조회 대상 처리
		criteriaMultiSelectQuery.multiselect(m2.get("username"), m2.get("age"));

		TypedQuery<Object[]> query2 = entityManager.createQuery(criteriaMultiSelectQuery);
		List<Object[]> result = query2.getResultList();

		result.forEach((data) -> {
			System.out.println(String.format("name : %s, age : %s", data[0], data[1]));
		});
	}

	private void searchDistinctTeam() {
		System.out.println("Member들의 팀 데이터 조회, 중복 제거");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Team10> criteriaQuery = criteriaBuilder.createQuery(Team10.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);

		criteriaQuery.select(m.get("team")).distinct(true);

		TypedQuery<Team10> query = entityManager.createQuery(criteriaQuery);
		List<Team10> teams = query.getResultList();
		teams.forEach(System.out::println);
	}

	private void convertWithConstruct() {
		System.out.println("Criteria의 contruct 함수를 사용하여 조회 결과를 별도 객체로 매핑");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserDto10> criteriaQuery = criteriaBuilder.createQuery(UserDto10.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery.select(criteriaBuilder.construct(UserDto10.class, m.get("username"), m.get("age")));

		TypedQuery<UserDto10> query = entityManager.createQuery(criteriaQuery);
		List<UserDto10> users = query.getResultList();
		users.forEach(System.out::println);
	}

	private void searchTypeTuple() {
		System.out.println("Criteria의 Tuple 타입으로 데이터를 반환받아, 이름 기반 매핑 작업 수행");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery.multiselect(
			m.get("username").alias("name"),
			m.get("age").alias("age")
		);

		TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
		List<Tuple> tuples = query.getResultList();
		tuples.forEach((tuple -> {
			System.out.println(String.format("name : %s, age : %s", tuple.get("name"), tuple.get("age")));
		}));
	}

	private void searchGroupByTeam() {
		System.out.println("Criteria의 GroupBy() 함수를 사용하여 member들을 Team 단위로 묶어서 조회");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);

		Expression maxAge = criteriaBuilder.max(m.get("age"));
		Expression minAge = criteriaBuilder.min(m.get("age"));

		criteriaQuery.multiselect(m.get("team"), maxAge, minAge)
			.groupBy(m.get("team"))
			.having(criteriaBuilder.ge(minAge, 10));

		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		List<Object[]> objects = query.getResultList();
		objects.forEach((obj) -> {
			System.out.println(String.format("TeamName : %s, maxAge : %d, minAge : %d", obj[0], obj[1], obj[2]));
		});
	}

	private void searchMemberOrderByAge() {
		System.out.println("Criteria의 OrderBy() 함수를 사용하여 member들을 age 단위로 정렬");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery.select(m)
			.orderBy(criteriaBuilder.desc(m.get("age")));

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}
}
