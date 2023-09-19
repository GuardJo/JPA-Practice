package com.practice.jpa.chapter06.data;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_MEMBER_5S_CH06_PRODUCT_2S")
@IdClass(value = MemberProductId.class)
public class MemberProduct {
	@Id
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member6_5 member;
	@Id
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product6_2 product;

	private long orderAmount = 0;
	private LocalDate orderDate;

	protected MemberProduct() {

	}

	public MemberProduct(Member6_5 member, Product6_2 product) {
		this.member = member;
		this.product = product;
		this.orderAmount = 1;
		this.orderDate = LocalDate.now();
	}

	public Member6_5 getMember() {
		return member;
	}

	public Product6_2 getProduct() {
		return product;
	}

	public long getOrderAmount() {
		return orderAmount;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setMember(Member6_5 member) {
		this.member = member;
	}

	public void setProduct(Product6_2 product) {
		this.product = product;
	}
}
