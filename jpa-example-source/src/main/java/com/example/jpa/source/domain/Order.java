package com.example.jpa.source.domain;

import javax.persistence.*;

import java.util.Date;
import java.util.Objects;

/**
 * 주문 도메인
 * 회원 별로 여러 주문을 넣을 수 있다.
 * 주문은 주문 일시, 주문 상태 정보를 지니고 있다.
 *
 * TODO : 차후 회원 테이블과 연관관계 매핑할 예정
 */
@Entity
@Table(name = "ORDERS")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long id;
	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date orderDate;
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;
	@Column(name = "MEMBER_ID")
	private Long memberId;

	protected Order() {

	}

	protected Order(OrderStatus status) {
		this.status = status;
		this.orderDate = new Date();
	}

	public static Order of(OrderStatus status) {
		return new Order(status);
	}

	public Long getId() {
		return id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Order order = (Order)o;
		return Objects.equals(id, order.id) && Objects.equals(orderDate, order.orderDate) && status == order.status && Objects.equals(memberId,
			order.memberId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, orderDate, status, memberId);
	}

	@Override
	public String toString() {
		return "Order{" +
			"id=" + id +
			", orderDate=" + orderDate +
			", status=" + status +
			", memberId=" + memberId +
			'}';
	}
}
