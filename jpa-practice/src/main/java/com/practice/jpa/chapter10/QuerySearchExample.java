package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.mysema.query.jpa.impl.JPAQuery;
import com.practice.jpa.chapter10.domain.Member10;
import com.practice.jpa.chapter10.domain.QMember10;

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
		searchByCriteria();
		searchByQuerydsl();
		searchByNativeQuery();
		getJdbcConnection();
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

	private void searchByCriteria() {
		String searchName = "test2";
		System.out.println("Criteria 검색 쿼리 테스트 : name = " + searchName);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10> query = criteriaBuilder.createQuery(Member10.class);

		Root<Member10> m = query.from(Member10.class);

		CriteriaQuery<Member10> criteriaQuery = query.select(m).where(criteriaBuilder.equal(m.get("name"), searchName));

		List<Member10> members = entityManager.createQuery(criteriaQuery).getResultList();

		members.forEach(System.out::println);
	}

	private void searchByQuerydsl() {
		String searchName = "test2";
		System.out.println("QueryDSL 검색 쿼리 테스트 : name = " + searchName);

		JPAQuery query = new JPAQuery(entityManager);
		QMember10 member = QMember10.member10;

		List<Member10> members = query.from(member)
			.where(member.name.eq(searchName))
			.list(member);

		members.forEach(System.out::println);
	}

	private void searchByNativeQuery() {
		String searchName = "test2";
		System.out.println("Native Query 검색 테스트 : name = " + searchName);

		String query = "select *\n"
			+ "from CH10_MEMBER\n"
			+ "where NAME = '" + searchName + "'";

		List<Member10> members = entityManager.createNativeQuery(query, Member10.class).getResultList();

		members.forEach(System.out::println);
	}

	private void getJdbcConnection() {
		System.out.println("Jdbc Connection 취득 테스트");

		Session session = entityManager.unwrap(Session.class);

		session.doWork(connection -> {
			System.out.println("DB : " + connection.getMetaData().getDatabaseProductName());
			connection.close();
		});

		session.close();
	}
}
