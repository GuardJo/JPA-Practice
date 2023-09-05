package com.example.jpa.source;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.example.jpa.source.domain.Item;
import com.example.jpa.source.domain.Member;
import com.example.jpa.source.domain.Order;
import com.example.jpa.source.domain.OrderItem;
import com.example.jpa.source.domain.OrderStatus;

public class EntityService implements Runnable {
	private final EntityManager entityManager;

	public EntityService(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		System.out.println("실습 예제 테스트");
		createEntities(this.entityManager);
	}

	private void createEntities(EntityManager entityManager) {
		System.out.println("테스트 데이터 생성 및 저장");
		Member member = Member.of("tester", "seoul");
		Item item = Item.of("testItem", 1000, 1);

		Order order = Order.of(OrderStatus.ORDER);
		OrderItem orderItem = OrderItem.of("testOrderItem", 10000);

		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		entityManager.persist(member);
		entityManager.persist(item);

		order.setMemberId(member.getId());
		entityManager.persist(order);

		orderItem.setOrderId(order.getId());
		orderItem.setItemId(item.getId());
		entityManager.persist(orderItem);

		transaction.commit();
	}
}
