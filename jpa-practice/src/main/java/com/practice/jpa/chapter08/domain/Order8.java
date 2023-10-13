package com.practice.jpa.chapter08.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CH08_ORDER")
public class Order8 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID", nullable = false)
	private Member8 member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product8 product;

	protected Order8() {

	}

	public static Order8 create() {
		return new Order8();
	}

	public void setMember(Member8 member) {

		if (member != null) {
			member.getOrders().add(this);
		}

		if (this.member != null) {
			this.member.getOrders().remove(this);
		}
		this.member = member;
	}

	public void setProduct(Product8 product) {
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public Member8 getMember() {
		return member;
	}

	public Product8 getProduct() {
		return product;
	}

	@Override
	public String toString() {
		return "Order8{" +
			"id=" + id +
			", member=" + member +
			", product=" + product +
			'}';
	}
}
