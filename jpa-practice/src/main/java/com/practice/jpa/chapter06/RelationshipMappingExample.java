package com.practice.jpa.chapter06;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.practice.jpa.chapter06.data.Locker;
import com.practice.jpa.chapter06.data.Member6;
import com.practice.jpa.chapter06.data.Member6_2;
import com.practice.jpa.chapter06.data.Member6_3;
import com.practice.jpa.chapter06.data.Member6_4;
import com.practice.jpa.chapter06.data.Product;
import com.practice.jpa.chapter06.data.Team6;
import com.practice.jpa.chapter06.data.Team6_2;

public class RelationshipMappingExample implements Runnable {
	private final EntityManager entityManager;

	public RelationshipMappingExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		System.out.println("다양한 연관관계 매핑 테스트");
		saveEntitiesNtoOne();
		traverseEntitiesNtoOne();

		saveEntitiesOneToN();
		traverseEntitiesOneToN();

		saveEntitiesOneToOneWithOwner();
		traverseEntitiesOnetoOne();

		saveEntitiesNtoN();
		traverseEntitiesNtoN();
	}

	private void saveEntitiesNtoOne() {
		System.out.println("[다대일] Meber 및 Team 객체 저장");

		Team6 team = new Team6("testTeam");
		Member6 member = new Member6("testMember");
		member.setTeam(team);

		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(team);
		entityManager.persist(member);

		transaction.commit();
	}

	private void saveEntitiesOneToN() {
		System.out.println("[일대다] Meber 및 Team 객체 저장");

		Member6_2 member = new Member6_2("testMember");
		Team6_2 team = new Team6_2("testTeam");
		team.getMembers().add(member);

		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(team);
		entityManager.persist(member);

		transaction.commit();
	}

	private void saveEntitiesOneToOneWithOwner() {
		System.out.println("[일대일] Member 및 Locker 객체 저장");

		Member6_3 member = new Member6_3("testMember");
		Locker locker = new Locker("testLocker");

		locker.setMember(member);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(locker);
		entityManager.persist(member);

		transaction.commit();
	}

	private void saveEntitiesNtoN() {
		System.out.println("[다대다] Member 및 Product 객체 저장");
		Member6_4 member = new Member6_4("testMember");
		Product product = new Product("testProduct");

		member.getProducts().add(product);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(product);
		entityManager.persist(member);
		transaction.commit();
	}

	private void traverseEntitiesNtoOne() {
		System.out.println("[다대일] Member 및 Team 객체 그래프 탐색");

		long memberId = 1L;

		Member6 member = entityManager.find(Member6.class, memberId);
		Team6 team = member.getTeam();

		System.out.println(member.toString());
		System.out.println(team.toString());

		System.out.println("Members in Team");
		team.getMembers().forEach(item -> System.out.println(item.toString()));
	}

	private void traverseEntitiesOneToN() {
		System.out.println("[일대다] Member 및 Team 객체 그래프 탐색");

		long memberId = 1L;

		Member6 member = entityManager.find(Member6.class, memberId);
		Team6 team = member.getTeam();

		System.out.println(member.toString());
		System.out.println(team.toString());
	}

	private void traverseEntitiesOnetoOne() {
		System.out.println("[일대일] Member 및 Locker 객체 그래프 탐색");

		long memberId = 1L;

		Member6_3 member = entityManager.find(Member6_3.class, memberId);
		Locker locker = member.getLocker();

		System.out.println(member);
		System.out.println(locker);
		System.out.println(locker.getMember());
	}

	private void traverseEntitiesNtoN() {
		System.out.println("[다대다] Member 및 Product 객체 그래프 탐색");

		long memberId = 1L;

		Member6_4 member = entityManager.find(Member6_4.class, memberId);
		List<Product> products = member.getProducts();

		System.out.println(member);
		products.forEach(System.out::println);
	}
}
