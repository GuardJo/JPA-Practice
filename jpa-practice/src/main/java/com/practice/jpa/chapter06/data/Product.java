package com.practice.jpa.chapter06.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_PRODUCT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productName;

	@ManyToMany(mappedBy = "products")
	private List<Member6_4> members = new ArrayList<>();

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

	public List<Member6_4> getMembers() {
		return Collections.unmodifiableList(this.members);
	}

	protected void addMember(Member6_4 member) {
		if (this.members.contains(member)) {
			return;
		}

		this.members.add(member);
	}

	protected void removeMember(Member6_4 member) {
		this.members.remove(member);
	}

	@Override
	public String toString() {
		return "Product{" +
			"id=" + id +
			", productName='" + productName + '\'' +
			'}';
	}
}
