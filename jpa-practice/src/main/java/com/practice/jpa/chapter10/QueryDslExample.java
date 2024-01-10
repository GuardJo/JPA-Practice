package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.mysema.query.QueryModifiers;
import com.mysema.query.SearchResults;
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
		searchByMembersOrderByAge();
		searchByMembersOrderByAge2();
		searchByTotalCountWithPagination();
		groupByMembers();
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
}
