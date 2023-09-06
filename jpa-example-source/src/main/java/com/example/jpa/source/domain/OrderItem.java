package com.example.jpa.source.domain;

import javax.persistence.*;

import java.util.Objects;

/**
 * 주문 상품 도메인
 * 여러 상품 중 주문한 상품에 대한 정보를 지니고 있다. (주문 상품명, 주문 상품 가격, 주문한 개수)
 *
 * TODO : 차후 상품 도메인과 주문 도메인과 연관 관계를 매핑할 예정
 */
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int price;
	@Column(nullable = false)
	private int count = 0;
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	protected OrderItem() {

	}

	private OrderItem(String name, int price) {
		this.name = name;
		this.price = price;
		this.count++;
	}

	public static OrderItem of(String name, int price) {
		return new OrderItem(name, price);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrderItem orderItem = (OrderItem)o;
		return price == orderItem.price && count == orderItem.count && Objects.equals(id, orderItem.id) && Objects.equals(name,
			orderItem.name) && Objects.equals(item, orderItem.item) && Objects.equals(order, orderItem.order);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, count, item, order);
	}

	@Override
	public String toString() {
		return "OrderItem{" +
			"id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", count=" + count +
			", itemId=" + item.getId() +
			", orderId=" + order.getId() +
			'}';
	}
}
