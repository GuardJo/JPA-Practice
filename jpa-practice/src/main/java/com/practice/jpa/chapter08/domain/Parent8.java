package com.practice.jpa.chapter08.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH08_PARENT")
public class Parent8 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Child8> childs = new ArrayList<>();

	protected Parent8() {

	}

	private Parent8(String name) {
		this.name = name;
	}

	public static Parent8 of(String name) {
		return new Parent8(name);
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

	public List<Child8> getChilds() {
		return childs;
	}

	@Override
	public String toString() {
		return "Parent8{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
