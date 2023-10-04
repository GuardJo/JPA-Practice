package com.practice.jpa.chapter08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

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

	private void printMemberAndTeam() {
		Member8 member = entityManager.find(Member8.class, 1L);

		System.out.println(member);
		System.out.println(member.getTeam());
	}

	private void printMember() {
		Member8 member = entityManager.find(Member8.class, 1L);

		System.out.println(member);
	}

	private void printMemberProxy() {
		entityManager.clear();

		Member8 member8 = entityManager.getReference(Member8.class, 1L);

		System.out.println(member8);
	}

	private void printMemberAndTeamProxy() {
		entityManager.clear();

		Member8 member = entityManager.getReference(Member8.class, 1L);

		System.out.println(member);

		entityManager.clear(); // Persistence Context를 비움으로써 Member Entity를 준영속 상태로 전환
		System.out.println(member.getTeam());
	}
}
