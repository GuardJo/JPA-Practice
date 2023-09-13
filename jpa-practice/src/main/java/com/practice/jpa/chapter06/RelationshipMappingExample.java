package com.practice.jpa.chapter06;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.practice.jpa.chapter06.data.Member6;
import com.practice.jpa.chapter06.data.Team6;

public class RelationshipMappingExample implements Runnable {
	private final EntityManager entityManager;

	public RelationshipMappingExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		System.out.println("다양한 연관관계 매핑 테스트");
		saveEntities();
		traverseEntities();
	}

	private void saveEntities() {
		System.out.println("Meber 및 Team 객체 저장");

		Team6 team = new Team6("testTeam");
		Member6 member = new Member6("testMember");
		member.setTeam(team);

		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(team);
		entityManager.persist(member);

		transaction.commit();
	}

	private void traverseEntities() {
		System.out.println("Member 및 Team 객체 그래프 탐색");

		long memberId = 1L;

		Member6 member = entityManager.find(Member6.class, memberId);
		Team6 team = member.getTeam();

		System.out.println(member.toString());
		System.out.println(team.toString());

		System.out.println("Members in Team");
		team.getMembers().forEach(item -> System.out.println(item.toString()));
	}
}
