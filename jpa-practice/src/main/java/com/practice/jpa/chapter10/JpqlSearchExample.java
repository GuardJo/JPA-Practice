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
import com.practice.jpa.chapter10.dto.TeamAvgAgeDto;
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
		findWithPagination();
		calculateReport();
		searchMemberGroupByTeam();
		searchMemberSortAge();
		searchMemberInnerJoinTeam();
		searchMemberOuterJoinTeam();
		searchMemberAndTeamWithCollectionJoin();
		searchThetaJoinWithMemberAndTeam();
		searchJoinOnWithMemberAndTeam();
		searchFetchJoinMemberAndTeam();
		searchFetchJoinTeamAndMember();
		searchFetchJoinTeamAndMemberWithDistinct();
		searchPathFromStateField();
		searchPathFromAssociationField();
		searchPathFromCollectionField();
		searchPathContinueFromCollectionField();
		countMembersFromTeam();
		searchHigherAgeAvg();
		searchMembersOfHaveOrders();
		subQueryExistsFunction();
		subQueryAllFunction();
		subQueryAnyFunction();
		subQueryInFunction();
		collectionExpressionEmptyQuery();
		collectionExpressionMembersQuery();
		caseExpressionCoalesceQuery();
		caseExpressionNullIfQuery();
	}

	private void initData() {
		Product10 product = Product10.create("New Product", 3000, 1);
		Team10 team = Team10.create("New Team");
		Member10_2 member = Member10_2.create("Tester", 28);
		member.setTeam(team);
		Address10 address = Address10.create("SEOUL", "GIL", "100-111");
		Order10 order = Order10.create(5, address, member, product);
		Member10_2 member2 = Member10_2.create("tester2", 30);
		Member10_2 member3 = Member10_2.create("Human", 15);
		Member10_2 member4 = Member10_2.create(null, 18);
		member2.setTeam(team);
		member3.setTeam(team);
		member4.setTeam(team);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(order);
		entityManager.persist(member2);
		entityManager.persist(member3);
		entityManager.persist(member4);

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

	private void findWithPagination() {
		System.out.println("데이터 목록 조회 시 Pagination 처리 테스트");

		TypedQuery<Member10_2> query = entityManager.createQuery("select m from Member10_2 m order by m.age desc ", Member10_2.class);
		query.setFirstResult(10);
		query.setMaxResults(20);

		List<Member10_2> members = query.getResultList();

		members.forEach(System.out::println);
	}

	private void calculateReport() {
		System.out.println("통계 데이터 조회 테스트");

		TypedQuery<Long> countQuery = entityManager.createQuery("select count(m) from Member10_2 m", Long.class);
		long totalCount = countQuery.getSingleResult();
		System.out.println("COUNT : " + totalCount);

		TypedQuery<Integer> maxQuery = entityManager.createQuery("select max(m.age) from Member10_2 m", Integer.class);
		int maxAge = maxQuery.getSingleResult();
		System.out.println("MAX age : " + maxAge);

		TypedQuery<Double> avgQuery = entityManager.createQuery("select avg(m.age) from Member10_2 m", Double.class);
		double avgAge = avgQuery.getSingleResult();
		System.out.println("AVG age : " + avgAge);

		TypedQuery<Long> sumQuery = entityManager.createQuery("select sum(m.age) from Member10_2 m", Long.class);
		Long sumAge = sumQuery.getSingleResult();
		System.out.println("SUM age : " + sumAge);
	}

	private void searchMemberGroupByTeam() {
		System.out.println("GROUP BY 및 HAVING을 통한 Team별 Member 나이 평균 조회 테스트");

		TypedQuery<TeamAvgAgeDto> query = entityManager.createQuery(
			"select new com.practice.jpa.chapter10.dto.TeamAvgAgeDto(t.name, avg(m.age)) from Member10_2 m left join m.team t group by t.name having avg(m.age) >= 10",
			TeamAvgAgeDto.class);

		List<TeamAvgAgeDto> teamAvgAgeDtos = query.getResultList();
		teamAvgAgeDtos.forEach(System.out::println);
	}

	private void searchMemberSortAge() {
		System.out.println("ORDER BY를 활용하여 MEBER 중 age가 높은 순으로 정렬 테스트");

		TypedQuery<Member10_2> query = entityManager.createQuery("select m from Member10_2 m order by m.age desc", Member10_2.class);

		List<Member10_2> members = query.getResultList();

		members.forEach(System.out::println);
	}

	private void searchMemberInnerJoinTeam() {
		System.out.println("INNER JOIN을 통해 MEMBER와 TEAM 조회 테스트");
		String searchTeamName = "New Team";

		TypedQuery<Member10_2> query = entityManager.createQuery("select m from Member10_2 m inner join m.team t where t.name = :searchName",
			Member10_2.class);

		List<Member10_2> members = query
			.setParameter("searchName", searchTeamName)
			.getResultList();

		members.forEach(System.out::println);
	}

	private void searchMemberOuterJoinTeam() {
		System.out.println("OUTER JOIN을 통해 MEMBER와 TEAM 조회 테스트");

		TypedQuery<Member10_2> query = entityManager.createQuery("select m from Member10_2 m left outer join m.team", Member10_2.class);

		List<Member10_2> members = query.getResultList();

		members.forEach(System.out::println);
	}

	private void searchMemberAndTeamWithCollectionJoin() {
		System.out.println("Collection Join을 통한 Member 및 Team 조회 테스트");

		System.out.println("단일 값 조회 : Member -> Team");
		TypedQuery<Member10_2> memberQuery = entityManager.createQuery("select m from Member10_2 m left join m.team", Member10_2.class);
		List<Member10_2> members = memberQuery.getResultList();
		members.forEach(System.out::println);

		System.out.println("컬렉션 값 조회 : Team -> Member");
		TypedQuery<Team10> teamQuery = entityManager.createQuery("select t from Team10 t left join t.members", Team10.class);
		List<Team10> teams = teamQuery.getResultList();
		teams.forEach(System.out::println);
	}

	private void searchThetaJoinWithMemberAndTeam() {
		System.out.println("THETA JOIN을 통한 MEMBER명과 TEAM명 동일한 데이터 수 반환 테스트");

		TypedQuery<Long> countQuery = entityManager.createQuery("select count(m) from Member10_2 m, Team10 t where m.username = t.name", Long.class);
		long result = countQuery.getSingleResult();

		System.out.println("Total Count = " + result);
	}

	private void searchJoinOnWithMemberAndTeam() {
		System.out.println("JOIN ON을 사용하여 MEMBER와 TEAM을 조인 한 후 필터링하여 조회 테스트");

		String teamName = "new team";

		TypedQuery<Member10_2> query = entityManager.createQuery("select m from Member10_2 m left outer join m.team t on t.name = :searchTeamName",
			Member10_2.class);
		List<Member10_2> members = query
			.setParameter("searchTeamName", teamName)
			.getResultList();

		members.forEach(System.out::println);
	}

	private void searchFetchJoinMemberAndTeam() {
		entityManager.clear();
		System.out.println("Entity에 대한 FETCH JOIN을 사용하여 MEMBER와 TEAM을 즉시 로딩하여 조회 테스트");

		TypedQuery<Member10_2> query = entityManager.createQuery("select m from Member10_2 m join fetch m.team", Member10_2.class);
		List<Member10_2> members = query.getResultList();

		members.forEach(System.out::println);
	}

	private void searchFetchJoinTeamAndMember() {
		entityManager.clear();
		System.out.println("Collection에 대한 FETCH JOIN을 사용하여 TEAM과 MEMBER를 즉시 로딩하여 조회 테스트");

		TypedQuery<Team10> query = entityManager.createQuery("select t from Team10 t join fetch t.members", Team10.class);
		List<Team10> teams = query.getResultList();
		teams.forEach(System.out::println);
	}

	private void searchFetchJoinTeamAndMemberWithDistinct() {
		entityManager.clear();
		System.out.println("Collection에 대한 DISTINCT FETCH JOIN을 사용하여 TEAM과 MEMBER를 즉시 로딩하여 조회 테스트");

		TypedQuery<Team10> query = entityManager.createQuery("select distinct t from Team10 t join fetch t.members", Team10.class);
		List<Team10> teams = query.getResultList();
		teams.forEach(System.out::println);
	}

	private void searchPathFromStateField() {
		entityManager.clear();
		System.out.println("경로 탐색 : 상태 필드 경로 탐색 테스트");

		TypedQuery<String> query = entityManager.createQuery("select m.username from Member10_2 m", String.class);
		List<String> results = query.getResultList();
		results.forEach(System.out::println);
	}

	private void searchPathFromAssociationField() {
		entityManager.clear();
		System.out.println("경로 탐색 : 연관 필드 단일값 경로 탐색 테스트");

		TypedQuery<Team10> query = entityManager.createQuery("select distinct m.team from Member10_2 m", Team10.class);
		List<Team10> teams = query.getResultList();
		teams.forEach(System.out::println);
	}

	private void searchPathFromCollectionField() {
		entityManager.clear();
		System.out.println("경로 탐색 : 연관 필드 컬렉선 경로 텀색 테스트");

		// 여러 Entity 반환할 때에는 Object나 Object[]로 지정해야함
		TypedQuery<Object> query = entityManager.createQuery("select t.members from Team10 t", Object.class);
		List<Object> result = query.getResultList();

		result.forEach(System.out::println);
	}

	private void searchPathContinueFromCollectionField() {
		entityManager.clear();
		System.out.println("경로 탐색 : 연관 필드 컬렉션 내 연관 필드로 재탐색 테스트");

		// 해당 컬렉션에서 곧바로 연관 필드 탐색을 할 수 없음 (컬렉션이 탐색의 끝임)
		// TypedQuery<String> query = entityManager.createQuery("select t.members.username from Team10 t", String.class);

		TypedQuery<String> query = entityManager.createQuery("select m.username from Team10 t join t.members m", String.class);
		List<String> result = query.getResultList();

		result.forEach(System.out::println);
	}

	private void countMembersFromTeam() {
		entityManager.clear();
		System.out.println("경로 탐색 : 연관 필드 컬렉션 경로 탐색을 통해 특정 컬렉션의 총 개수 반환 테스트");

		TypedQuery<Integer> query = entityManager.createQuery("select size(t.members) from Team10 t", Integer.class);
		int result = query.getSingleResult();
		System.out.println("total = " + result);
	}

	private void searchHigherAgeAvg() {
		entityManager.clear();
		System.out.println("서브 쿼리 : 회원의 나이가 전체 평균 이상인 회원 목록 반환 테스트");

		TypedQuery<Member10_2> query = entityManager.createQuery("select m from Member10_2 m where m.age > (select avg(m2.age) from Member10_2 m2)",
			Member10_2.class);

		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void searchMembersOfHaveOrders() {
		entityManager.clear();
		System.out.println("서브 쿼리 : 주문 수가 1개 이상인 회원 목록 반환 테스트");

		TypedQuery<Member10_2> query = entityManager.createQuery(
			"select m from Member10_2 m where (select count(o) from Order10 o where m = o.member) > 0", Member10_2.class);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void subQueryExistsFunction() {
		entityManager.clear();
		System.out.println("서브 쿼리 함수 : Exists 함수를 통해 특정 Team명이 존재하는지 여부 반환 테스트");
		String searchTeamName = "New Team";

		TypedQuery<Team10> query = entityManager.createQuery(
			"select t from Team10 t where exists(select t2 from Team10 t2 where t2.name = :searchName)", Team10.class);
		query.setParameter("searchName", searchTeamName);

		List<Team10> teams = query.getResultList();
		teams.forEach(System.out::println);
	}

	private void subQueryAllFunction() {
		entityManager.clear();
		System.out.println("서브 쿼리 함수 : ALL 함수를 통해 모든 조건(전체 상품 각각의 재고보다 주문량이 많은)에 대해 참인 요소 반환 테스트");

		TypedQuery<Order10> query = entityManager.createQuery(
			"select o from Order10 o where o.orderAmount > all(select p.stockAmount from Product10 p)", Order10.class);
		List<Order10> orders = query.getResultList();
		orders.forEach(System.out::println);
	}

	private void subQueryAnyFunction() {
		entityManager.clear();
		System.out.println("서브 쿼리 함수 : ANY 함수를 통해 어느 조건(어떤 Team이든 존재하는 Member)에 대해 참인 요소 반환 테스트");

		TypedQuery<Member10_2> query = entityManager.createQuery("select m from Member10_2 m where m.team = any(select t from Team10 t)",
			Member10_2.class);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}

	private void subQueryInFunction() {
		entityManager.clear();
		System.out.println("서브 쿼리 함수 : IN 함수를 통해 쿼리 결과 중 하나라도 같은 것이 있으면(20세 이상인 Member를 보유한 Team) 참 반환 테스트");

		TypedQuery<Team10> query = entityManager.createQuery(
			"select t from Team10 t where t in (select t2 from Team10 t2 join t2.members m2 where m2.age >= 20)", Team10.class);
		List<Team10> teams = query.getResultList();
		teams.forEach(System.out::println);
	}

	private void collectionExpressionEmptyQuery() {
		entityManager.clear();
		System.out.println("조건식 (컬렉션 식) : 컬렉션이 비어있는지 여부에 따른 조건 검색 테스트");

		TypedQuery<Team10> query = entityManager.createQuery("select t from Team10 t where t.members is not empty", Team10.class);
		List<Team10> teams = query.getResultList();

		teams.forEach(System.out::println);
	}

	private void collectionExpressionMembersQuery() {
		entityManager.clear();
		System.out.println("조건식 (컬렉션 식) : 컬렉션에 특정 엔티티가 포함되어 있는지 여부에 따른 조건 검색 테스트");

		TypedQuery<Member10_2> findMember = entityManager.createQuery("select m from Member10_2 m", Member10_2.class);
		Member10_2 member = findMember.getResultList().get(0);

		TypedQuery<Team10> query = entityManager.createQuery("select t from Team10 t where :memberParam member of t.members", Team10.class);
		query.setParameter("memberParam", member);
		List<Team10> teams = query.getResultList();
		teams.forEach(System.out::println);
	}

	private void caseExpressionCoalesceQuery() {
		entityManager.clear();
		System.out.println("조건식 (CASE 식 : COALESCE) : Member 이름 목록 반환, 이 때 name이 null이면 '익명'으로 반환");

		TypedQuery<String> query = entityManager.createQuery("select coalesce(m.username, '익명') from Member10_2 m", String.class);
		List<String> result = query.getResultList();

		result.forEach(System.out::println);
	}

	private void caseExpressionNullIfQuery() {
		entityManager.clear();
		System.out.println("조건식 (CASE 식 : NULLIF) : Member 이름이 'Human'이면 null을 반환, 아닐 경우는 이름 그대로 반환");

		TypedQuery<String> query = entityManager.createQuery("select nullif(m.username, 'Human') from Member10_2 m", String.class);
		List<String> result = query.getResultList();
		result.forEach(System.out::println);
	}
}
