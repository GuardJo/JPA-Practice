package com.practice.jpa.chapter08.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CH08_CHILD")
public class Child8 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Parent8 parent;

	protected Child8() {

	}

	private Child8(String name) {
		this.name = name;
	}

	public static Child8 of(String name) {
		return new Child8(name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Parent8 getParent() {
		return parent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Parent8 parent) {
		if (parent != null) {
			parent.getChilds().add(this);
		}

		if (this.parent != null) {
			this.parent.getChilds().remove(this);
		}

		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Child8{" +
			"id=" + id +
			", name='" + name + '\'' +
			", parent=" + parent +
			'}';
	}
}
