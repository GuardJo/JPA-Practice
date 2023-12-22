package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.practice.jpa.chapter10.domain.Member10_2;

public class CriteriaExample implements Runnable {
	private final EntityManager entityManager;

	public CriteriaExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		searchAllMemberWithCriteria();
		searchMemberOnlyAdult();
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

	private void searchMemberOnlyAdult() {
		System.out.println("Criteria를 통한 Member 조회 중 20세 이상인 Member들만 조회");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member10_2> criteriaQuery = criteriaBuilder.createQuery(Member10_2.class);

		Root<Member10_2> m = criteriaQuery.from(Member10_2.class);
		Predicate predicate = criteriaBuilder.ge(m.get("age"), 20);
		Order order = criteriaBuilder.desc(m.get("age"));
		criteriaQuery = criteriaQuery
			.select(m)
			.where(predicate)
			.orderBy(order);

		TypedQuery<Member10_2> query = entityManager.createQuery(criteriaQuery);
		List<Member10_2> members = query.getResultList();
		members.forEach(System.out::println);
	}
}
