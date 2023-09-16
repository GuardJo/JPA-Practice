package com.practice.jpa.chapter06.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_PRODUCT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productName;

	protected Product() {

	}

	public Product(String productName) {
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

	@Override
	public String toString() {
		return "Product{" +
			"id=" + id +
			", productName='" + productName + '\'' +
			'}';
	}
}
