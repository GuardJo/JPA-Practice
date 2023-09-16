package com.practice.jpa.chapter06.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_MEMBER_4")
public class Member6_4 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;

	@ManyToMany
	@JoinTable(
		name = "CH06_MEMBER_4S_CH06_PRODUCTS",
		joinColumns = @JoinColumn(name = "member_id"),
		inverseJoinColumns = @JoinColumn(name = "product_id")
	)
	private List<Product> products = new ArrayList<>();

	protected Member6_4() {

	}

	public Member6_4(String username) {
		this.username = username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public List<Product> getProducts() {
		return Collections.unmodifiableList(this.products);
	}

	public void addProduct(Product product) {
		if (this.products.contains(product)) {
			return;
		}

		product.addMember(this);
		this.products.add(product);
	}

	public void removeProduct(Product product) {
		product.removeMember(this);
		this.products.remove(product);
	}

	@Override
	public String toString() {
		return "Member6_4{" +
			"id=" + id +
			", username='" + username + '\'' +
			'}';
	}
}
