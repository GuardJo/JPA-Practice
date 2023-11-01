package com.practice.jpa.chapter10.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CH10_ORDERS")
public class Order10 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int orderAmount;

	@Embedded
	private Address10 address;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "PRODUCT_ID")
	private Product10 product;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "MEMBER_ID")
	private Member10_2 member;

	protected Order10() {

	}

	private Order10(int orderAmount, Address10 address, Member10_2 member, Product10 product) {
		this.orderAmount = orderAmount;
		this.address = address;
		this.member = member;
		this.product = product;
	}

	public static Order10 create(int orderAmount, Address10 address, Member10_2 member, Product10 product) {
		return new Order10(orderAmount, address, member, product);
	}

	public Long getId() {
		return id;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public Address10 getAddress() {
		return address;
	}

	public Product10 getProduct() {
		return product;
	}

	@Override
	public String toString() {
		return "Order10{" +
			"id=" + id +
			", orderAmount=" + orderAmount +
			", address=" + address +
			", product=" + product +
			", member=" + member +
			'}';
	}
}
