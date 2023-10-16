package com.example.jpa.source.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 주문 도메인
 * 회원 별로 여러 주문을 넣을 수 있다.
 * 주문은 주문 일시, 주문 상태 정보를 지니고 있다.
 *
 * TODO : 차후 회원 테이블과 연관관계 매핑할 예정
 */
@Entity
@Table(name = "ORDERS")
public class Order extends MetaData {
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
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;

	@OneToMany(mappedBy = "order")
	private final List<OrderItem> orderItems = new ArrayList<>();

	protected Order() {

	}

	protected Order(OrderStatus status) {
		this.status = status;
		this.orderDate = new Date();
		this.createdDate = LocalDateTime.now();
		this.modifiedDate = LocalDateTime.now();
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		if (this.member != null) {
			// 기존 관계 제거
			this.member.getOrders().remove(this);
		}

		if (member != null) {
			// 반대편에도 관계에 대한 참조 데이터 추가
			member.getOrders().add(this);
		}

		this.member = member;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Order order = (Order)o;
		return Objects.equals(id, order.id) && Objects.equals(orderDate, order.orderDate) && status == order.status
			&& Objects.equals(member, order.member);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, orderDate, status, member);
	}

	@Override
	public String toString() {
		return "Order{" +
			"id=" + id +
			", orderDate=" + orderDate +
			", status=" + status +
			", memberId=" + member.getId() +
			", deliveryId=" + delivery.getId() +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}
}
