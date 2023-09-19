package com.practice.jpa.chapter06.data;

import java.io.Serializable;
import java.util.Objects;

public class MemberProductId implements Serializable {
	private Long member;
	private Long product;

	public Long getMember() {
		return member;
	}

	public Long getProduct() {
		return product;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MemberProductId that = (MemberProductId)o;
		return Objects.equals(member, that.member) && Objects.equals(product, that.product);
	}

	@Override
	public int hashCode() {
		return Objects.hash(member, product);
	}
}
