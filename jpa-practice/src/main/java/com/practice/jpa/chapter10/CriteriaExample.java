package com.practice.jpa.chapter10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.practice.jpa.chapter10.domain.Member10_2;
import com.practice.jpa.chapter10.domain.Member10_2_;
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
		searchMemberWithJoinTeam();
		searchMemberWithSubQuery();
		searchMemberInTeam();
		searchMemberCase();
		searchMemberWithParams();
		executeNativeQueryFunction();
		searchDynamicQuery(20, "Tester", "New Team");
		searchMemberWithMetaModel();
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

	private void searchMemberWithJoinTeam() {
		System.out.println("Criteria의 join() 함수를 사용하여 Member와 Team 조인 및 특정 Team에 포함된 Member 조회");
		String teamName = "New Team";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		Join<Member10_2, Team10> t = m.join("team", JoinType.INNER);

		criteriaQuery.select(m)
			.where(criteriaBuilder.equal(t.get("name"), teamName));

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void searchMemberWithSubQuery() {
		System.out.println("Criteria의 SubQuery 객체를 활용하여 Member의 평균 나이 이상인 Member 조회");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);

		Subquery<Double> subquery = criteriaQuery.subquery(Double.class);
		Root<Member10_2> m2 = subquery.from(Member10_2.class);
		subquery.select(criteriaBuilder.avg(m2.get("age")));

		criteriaQuery.select(m)
			.where(criteriaBuilder.ge(m.get("age"), subquery));

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void searchMemberWithSubQuery2() {
		System.out.println("Criteria의 Subquery 객체 및 correlate() 함수를 활용하여 메인 쿼리의 요소를 기반으로 서브 쿼리 구성 및 조건에 따른 Member 조회");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);

		Subquery<Team10> subquery = criteriaQuery.subquery(Team10.class);
		Root<Member10_2> subM = subquery.correlate(m);
		Join<Member10_2, Team10> t = subM.join("team", JoinType.INNER);
		subquery.where(criteriaBuilder.equal(t.get("name"), "New Team"));

		criteriaQuery.select(m)
			.where(criteriaBuilder.exists(subquery));

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void searchMemberInTeam() {
		System.out.println("Criteria의 in() 함수를 활용하여 특정 Team에 속해 있는 Member 조회");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		Subquery<Team10> subquery = criteriaQuery.subquery(Team10.class);
		Root<Team10> t2 = subquery.from(Team10.class);
		subquery.select(t2)
			.where(criteriaBuilder.equal(t2.get("name"), "New Team"));

		criteriaQuery.select(m)
			.where(criteriaBuilder.in(subquery)
				.value(m.get("team")));

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void searchMemberCase() {
		System.out.println("Criteria의 selectCase(), when() 함수를 활용하여 조건에 따른 분기 처리");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery.select(
			criteriaBuilder.selectCase()
				.when(criteriaBuilder.ge(m.get("age"), 20), "Adult")
				.otherwise("Baby")
				.as(String.class)
		);

		TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
		List<String> result = query.getResultList();
		result.forEach(System.out::println);
	}

	private void searchMemberWithParams() {
		System.out.println("Criteria의 parameter() 함수를 활용하여, 별도 파라미터에 대한 값주입으로 조회");
		String memberName = "Tester";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery.select(m)
			.where(criteriaBuilder.equal(m.get("username"), criteriaBuilder.parameter(String.class, "memberName")));

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery)
			.setParameter("memberName", memberName);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void executeNativeQueryFunction() {
		System.out.println("Criteria의 function() 함수를 활용하여, CustomDialect에 구성된 Narive 함수 수행");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery.select(criteriaBuilder.function("test_concat", String.class, m.get("username")));

		TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
		List<String> result = query.getResultList();
		result.forEach(System.out::println);
	}

	private void searchDynamicQuery(int age, String username, String teamName) {
		System.out.println("Criteria를 활용하여 검색 인자 유무에 따른 동적 쿼리 조회");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		Join<Member10_2, Team10> t = m.join("team");

		List<Predicate> predicates = new ArrayList<>();
		if (age >= 0) {
			predicates.add(criteriaBuilder.ge(m.get("age"), age));
		}
		if (!Objects.isNull(username)) {
			predicates.add(criteriaBuilder.equal(m.get("username"), username));
		}
		if (!Objects.isNull(teamName)) {
			predicates.add(criteriaBuilder.equal(t.get("name"), teamName));
		}

		criteriaQuery.select(m)
			.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void searchMemberWithMetaModel() {
		System.out.println("Criteria 메타 모델을 기반으로 데이터 조회");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		criteriaQuery.select(m)
			.where(criteriaBuilder.equal(m.get(Member10_2_.username), "Tester"));

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}
}
