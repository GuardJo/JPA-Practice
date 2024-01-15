package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.mysema.query.QueryModifiers;
import com.mysema.query.SearchResults;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.Projections;
import com.practice.jpa.chapter10.domain.Member10_2;
import com.practice.jpa.chapter10.domain.Product10;
import com.practice.jpa.chapter10.domain.QMember10_2;
import com.practice.jpa.chapter10.domain.QProduct10;
import com.practice.jpa.chapter10.domain.QTeam10;
import com.practice.jpa.chapter10.domain.Team10;
import com.practice.jpa.chapter10.dto.UserDto10;

public class QueryDslExample implements Runnable {
	private final EntityManager entityManager;

	public QueryDslExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		initProducts();
		searchAllMembers();
		searchByNameMembers();
		searchByMembersOrderByAge();
		searchByMembersOrderByAge2();
		searchByTotalCountWithPagination();
		groupByMembers();
		searchByMemberInnerJoinTeam();
		searchByMemberInnerJoinTeamOnName();
		searchByMemberFetchJoinTeam();
		searchByMemberThetaJoinTeam();
		searchTeamInMember();
		searchTeamInUniqueMember();
		searchMemberName();
		searchMemberNameAndTeamName();
		searchMemberToUserDto();
		searchMemberToUserDtoWithFields();
		searchMemberToUserDtoWithConstructor();
		updateProductsByName();
		deleteProductsByName();
	}

	private void initProducts() {
		Product10 product1 = Product10.create("Product1", 3000, 10);
		Product10 product2 = Product10.create("Product2", 5000, 10);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(product1);
		entityManager.persist(product2);
		transaction.commit();
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

	private void searchByMembersOrderByAge() {
		System.out.println("QueryDSL을 활용하여 Member10_2 목록을 나이 순으로 정렬 및 페이징처리 하여 반환");

		JPAQuery jpaQuery = new JPAQuery(entityManager);
		QMember10_2 qMember = QMember10_2.member10_2;

		List<Member10_2> members = jpaQuery.from(qMember)
			.offset(2)
			.limit(2)
			.orderBy(qMember.age.desc())
			.list(qMember);

		members.forEach(System.out::println);
	}

	private void searchByMembersOrderByAge2() {
		System.out.println("QueryDSL을 활용하여 Member10_2 목록을 나이 순으로 정렬 및 페이징처리 하여 반환2");

		JPAQuery jpaQuery = new JPAQuery(entityManager);
		QMember10_2 qMember = QMember10_2.member10_2;
		QueryModifiers queryModifiers = new QueryModifiers(2L, 2L); // limit, offset

		List<Member10_2> members = jpaQuery.from(qMember)
			.restrict(queryModifiers)
			.orderBy(qMember.age.desc())
			.list(qMember);

		members.forEach(System.out::println);
	}

	private void searchByTotalCountWithPagination() {
		System.out.println("QueryDSL을 활용하여 페이징 처리에 따른 전체 데이터 수 반환");

		JPAQuery jpaQuery = new JPAQuery(entityManager);
		QMember10_2 qMember = QMember10_2.member10_2;

		SearchResults<Member10_2> searchResults = jpaQuery.from(qMember)
			.offset(2)
			.limit(2)
			.orderBy(qMember.age.desc())
			.listResults(qMember);

		long offset = searchResults.getOffset();
		long limit = searchResults.getLimit();
		long total = searchResults.getTotal();
		List<Member10_2> members = searchResults.getResults();

		System.out.printf("offset : %d, limit : %d, total : %d\n", offset, limit, total);
		members.forEach(System.out::println);
	}

	private void groupByMembers() {
		System.out.println("QueryDSL을 활용하여 팀별로 그룹핑 하여 팀별 평균 나이 조회");

		JPAQuery jpaQuery = new JPAQuery(entityManager);
		QMember10_2 qMember = QMember10_2.member10_2;

		List<Double> avgAges = jpaQuery.from(qMember)
			.groupBy(qMember.team)
			.list(qMember.age.avg());

		avgAges.forEach(avg -> System.out.printf("avg : %f\n", avg));
	}

	private void searchByMemberInnerJoinTeam() {
		System.out.println("QueryDSL을 활용하여 inner join을 통한 Member + Team 목록 반환");

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;
		QTeam10 qTeam = QTeam10.team10;

		List<Member10_2> members = jpaQuery.from(qMember)
			.innerJoin(qMember.team, qTeam)
			.list(qMember);

		members.forEach(System.out::println);
	}

	private void searchByMemberInnerJoinTeamOnName() {
		System.out.println("QueryDSL을 활용하여 inner join 및 on 함수를 통한 조건 검색 반환");

		String searchTeamName = "New Team";

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;
		QTeam10 qTeam = QTeam10.team10;

		List<Member10_2> members = jpaQuery.from(qMember)
			.join(qMember.team, qTeam)
			.on(qTeam.name.eq(searchTeamName))
			.list(qMember);

		members.forEach(System.out::println);
	}

	private void searchByMemberFetchJoinTeam() {
		System.out.println("QueryDSL을 활용하여 fetch join을 통해 Member <-> Team 검색 반환");

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;
		QTeam10 qTeam = QTeam10.team10;

		List<Member10_2> members = jpaQuery.from(qMember)
			.join(qMember.team, qTeam)
			.fetch()
			.list(qMember);

		members.forEach(System.out::println);
	}

	private void searchByMemberThetaJoinTeam() {
		System.out.println("QueryDSL을 활용하여 theta join을 통해 Member와 Team 검색 반환");

		String userName = "Tester";
		String teamName = "New Team";

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;
		QTeam10 qTeam = QTeam10.team10;

		List<Member10_2> members = jpaQuery.from(qMember, qTeam)
			.where(qMember.username.eq(userName).and(qTeam.name.eq(teamName)))
			.list(qMember);

		members.forEach(System.out::println);
	}

	private void searchTeamInMember() {
		System.out.println("QueryDSL을 활용하여 조건에 맞는 팀원이 특정 팀에 속해 있는지 여부를 서브쿼리로 조회");

		String usernName = "Tester";

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QTeam10 qTeam = QTeam10.team10;
		QMember10_2 qMember = QMember10_2.member10_2;

		List<Team10> teams = jpaQuery.from(qTeam)
			.where(qTeam.in(
				new JPASubQuery()
					.from(qMember)
					.where(qMember.username.eq(usernName))
					.list(qMember.team)
			))
			.list(qTeam);

		teams.forEach(System.out::println);
	}

	private void searchTeamInUniqueMember() {
		System.out.println("QueryDSL을 활용하여 조건에 맞는 팀원이 속해 있는 팀을 서브쿼리로 조회");

		long memberId = 1L;

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QTeam10 qTeam = QTeam10.team10;
		QMember10_2 qMember = QMember10_2.member10_2;

		List<Team10> teams = jpaQuery.from(qTeam)
			.where(qTeam.eq(
				new JPASubQuery()
					.from(qMember)
					.where(qMember.id.eq(memberId))
					.unique(qMember.team)
			))
			.list(qTeam);

		teams.forEach(System.out::println);
	}

	private void searchMemberName() {
		System.out.println("QueryDSL의 프로젝션 기능을 통해 단일 요소 (멤버 이름) 조회 요청");

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;

		List<String> memberNames = jpaQuery.from(qMember)
			.list(qMember.username);

		memberNames.forEach(System.out::println);
	}

	private void searchMemberNameAndTeamName() {
		System.out.println("QueryDSL의 프로젝션 기능을 통해 Tuple로 member의 이름과 속한 팀이름 조회 요청");

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;
		QTeam10 qTeam = QTeam10.team10;

		List<Tuple> tuples = jpaQuery.from(qMember)
			.join(qMember.team, qTeam)
			.list(qMember.username, qTeam.name);
		// .list(new QTuple(qMember.username, qTeam.name)) 과 동일

		tuples.forEach((tuple ->
			System.out.printf("username : %s, teamName : %s\n", tuple.get(qMember.username), tuple.get(qTeam.name))
		));
	}

	private void searchMemberToUserDto() {
		System.out.println("QueryDSL의 프로젝션 기능(bean)을 통해 조회된 Member들을 UserDTO로 반환");

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;

		List<UserDto10> userDtos = jpaQuery.from(qMember)
			.list(Projections.bean(UserDto10.class, qMember.username.as("name"), qMember.age));

		userDtos.forEach(System.out::println);
	}

	private void searchMemberToUserDtoWithFields() {
		System.out.println("QueryDSL의 프로젝션 기능(fields)을 통해 조회된 Member들을 UserDTO로 반환");

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;

		List<UserDto10> userDtos = jpaQuery.from(qMember)
			.list(Projections.fields(UserDto10.class, qMember.username.as("name"), qMember.age));

		userDtos.forEach(System.out::println);
	}

	private void searchMemberToUserDtoWithConstructor() {
		System.out.println("QueryDSL의 프로젝션 기능(constructor)을 통해 조회된 Member들을 UserDTO로 반환");

		JPAQuery jpaQuery = new JPAQuery(entityManager);

		QMember10_2 qMember = QMember10_2.member10_2;

		List<UserDto10> userDtos = jpaQuery.from(qMember)
			.list(Projections.constructor(UserDto10.class, qMember.username.as("name"), qMember.age));

		userDtos.forEach(System.out::println);
	}

	private void updateProductsByName() {
		System.out.println("QueryDSL의 JPAUpdateClause 객체를 통해 수정 배치 쿼리 요청");
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		String productName = "Product1";

		QProduct10 qProduct = QProduct10.product10;
		JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qProduct);

		long count = updateClause.where(qProduct.name.eq(productName))
			.set(qProduct.stockAmount, qProduct.stockAmount.add(1000))
			.execute();

		transaction.commit();

		System.out.printf("Update Count : %d\n", count);

		entityManager.clear();

		JPAQuery jpaQuery = new JPAQuery(entityManager);
		List<Product10> products = jpaQuery.from(qProduct)
			.list(qProduct);

		products.forEach(System.out::println);
	}

	private void deleteProductsByName() {
		System.out.println("QueryDSL의 JPADeleteClause 객체를 통해 삭제 배치 쿼리 요청");
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		String productName = "Product1";

		QProduct10 qProduct = QProduct10.product10;
		JPADeleteClause deleteClause = new JPADeleteClause(entityManager, qProduct);

		long count = deleteClause.where(qProduct.name.eq(productName))
			.execute();

		System.out.printf("Delete Count : %d\n", count);

		transaction.commit();
		entityManager.clear();

		JPAQuery jpaQuery = new JPAQuery(entityManager);
		List<Product10> products = jpaQuery.from(qProduct)
			.list(qProduct);

		products.forEach(System.out::println);
	}
}
