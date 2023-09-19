package com.practice.jpa.chapter06.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_PRODUCT_2")
public class Product6_2 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productName;

	@OneToMany(mappedBy = "product")
	private List<MemberProduct> memberProducts = new ArrayList<>();

	protected Product6_2() {

	}

	public Product6_2(String productName) {
		this.productName = productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public List<MemberProduct> getMemberProducts() {
		return memberProducts;
	}

	@Override
	public String toString() {
		return "Product{" +
			"id=" + id +
			", productName='" + productName + '\'' +
			'}';
	}
}
