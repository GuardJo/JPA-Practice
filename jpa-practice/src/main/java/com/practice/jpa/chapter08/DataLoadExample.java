package com.practice.jpa.chapter08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnitUtil;

import com.practice.jpa.chapter08.domain.Member8;
import com.practice.jpa.chapter08.domain.Team8;

public class DataLoadExample implements Runnable {
	private final EntityManager entityManager;

	public DataLoadExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		initData();

		printMember();
		printMemberAndTeam();
		printMemberProxy();
		printMemberAndTeamProxy();
		printMemberIdProxy();

		initNewTeamAndMember();
		setNewRelationshipWithProxy();
	}

	private void initData() {
		Team8 team = Team8.of("Test Team");
		Member8 member = Member8.of("Test Member");

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(team);
		member.setTeam(team);
		entityManager.persist(member);
		transaction.commit();
	}

	private void initNewTeamAndMember() {
		Team8 team = Team8.of("New Team");
		Member8 member = Member8.of("New Member");

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(team);
		entityManager.persist(member);
		transaction.commit();
	}

	private void printMemberAndTeam() {
		System.out.println("단일 Entity 및 연관관계 조회");

		Member8 member = entityManager.find(Member8.class, 1L);

		System.out.println(member);
		System.out.println(member.getTeam());
	}

	private void printMember() {
		System.out.println("단일 Entity 조회");

		Member8 member = entityManager.find(Member8.class, 1L);

		System.out.println(member);
	}

	private void printMemberProxy() {
		System.out.println("프록시 객체 조회");

		entityManager.clear();

		Member8 member8 = entityManager.getReference(Member8.class, 1L);

		System.out.println(member8.getName());
	}

	private void printMemberAndTeamProxy() {
		System.out.println("프록시 객체 및 연관관계 조회");

		entityManager.clear();

		Member8 member = entityManager.getReference(Member8.class, 1L);

		System.out.println(member.getName());
		System.out.println(member.getTeam());
	}

	private void printMemberIdProxy() {
		System.out.println("프록시 객체의 식별자값 조회 요청");

		entityManager.clear();

		Member8 member = entityManager.getReference(Member8.class, 1L);
		PersistenceUnitUtil persistenceUnitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();

		boolean isLoaded = persistenceUnitUtil.isLoaded(member);

		System.out.println(member.getId());
		System.out.println("isLoaded = " + isLoaded);
		System.out.println("Class Name = " + member.getClass().getName());
	}

	private void setNewRelationshipWithProxy() {
		System.out.println("프록시 객체를 활용한 연관관계 매핑");

		entityManager.clear();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		Member8 member = entityManager.find(Member8.class, 2L);
		Team8 team = entityManager.getReference(Team8.class, 2L);

		member.setTeam(team);
		transaction.commit();
	}
}
