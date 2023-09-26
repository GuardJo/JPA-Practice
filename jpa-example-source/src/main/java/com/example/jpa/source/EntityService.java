package com.example.jpa.source;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.example.jpa.source.domain.Album;
import com.example.jpa.source.domain.Book;
import com.example.jpa.source.domain.Category;
import com.example.jpa.source.domain.Delivery;
import com.example.jpa.source.domain.Item;
import com.example.jpa.source.domain.Member;
import com.example.jpa.source.domain.Movie;
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
		createEntities();
		traverseEntityObjects();
		traverseEntityObjectsWithTwinRelation();
	}

	private void createEntities() {
		System.out.println("테스트 데이터 생성 및 저장");
		Category category = Category.of("Basic Category");
		Member member = Member.of("tester", "seoul");
		Album album = Album.of("testAlbume", 1000, 1, "artist", "etc");
		Book book = Book.of("testBook", 2000, 1, "author", "1234F");
		Movie movie = Movie.of("testMovie", 25000, 1, "director", "actor");
		Delivery delivery = Delivery.of("city", "street", 100);

		Order order = Order.of(OrderStatus.ORDER);
		OrderItem orderItem = OrderItem.of("testOrderItem", 10000);
		OrderItem orderBook = OrderItem.of("testOrderBook", 1000);
		OrderItem orderMovie = OrderItem.of("testOrderMovie", 25000);

		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		entityManager.persist(category);
		entityManager.persist(member);
		entityManager.persist(album);
		entityManager.persist(book);
		entityManager.persist(movie);
		album.getCategories().add(category);
		book.getCategories().add(category);
		movie.getCategories().add(category);
		entityManager.persist(delivery);

		order.setMember(member);
		order.setDelivery(delivery);
		entityManager.persist(order);

		orderItem.setOrder(order);
		orderItem.setItem(album);
		entityManager.persist(orderItem);

		orderBook.setOrder(order);
		orderBook.setItem(book);
		entityManager.persist(orderBook);

		orderMovie.setOrder(order);
		orderMovie.setItem(movie);
		entityManager.persist(orderMovie);

		transaction.commit();
	}

	private void traverseEntityObjects() {
		System.out.println("객체 그래프 탐색");
		OrderItem orderItem = entityManager.find(OrderItem.class, 1L);
		Item item = orderItem.getItem();
		Order order = orderItem.getOrder();
		Member member = order.getMember();
		Delivery delivery = order.getDelivery();

		System.out.println(orderItem);
		System.out.println(item);
		System.out.println(order);
		System.out.println(member);
		System.out.println(delivery);

		item.getCategories().forEach(System.out::println);
	}

	private void traverseEntityObjectsWithTwinRelation() {
		System.out.println("양방향 객체 그래프 탐색");

		Member member = entityManager.find(Member.class, 1L);

		for (Order order : member.getOrders()) {
			System.out.println(order.toString());

			for (OrderItem orderItem : order.getOrderItems()) {
				System.out.println(orderItem);
				System.out.println(orderItem.getItem());
			}
		}
	}
}
