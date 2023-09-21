package com.example.jpa.source.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID")
	private Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Category parent;

	protected Category() {

	}

	private Category(String name) {
		this.name = name;
	}

	public static Category of(String name) {
		return new Category(name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Category getParent() {
		return parent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Category{" +
			"id=" + id +
			", name='" + name + '\'' +
			", parent=" + parent +
			'}';
	}
}
