package com.practice.jpa.chapter08.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CH08_PRODUCT")
public class Product8 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	protected Product8() {

	}

	private Product8(String name) {
		this.name = name;
	}

	public static Product8 of(String name) {
		return new Product8(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Product8{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
