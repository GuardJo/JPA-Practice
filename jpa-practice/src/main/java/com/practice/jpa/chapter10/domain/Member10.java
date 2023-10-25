package com.practice.jpa.chapter10.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CH10_MEMBER")
public class Member10 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	protected Member10() {

	}

	private Member10(String name) {
		this.name = name;
	}

	public static Member10 create(String name) {
		return new Member10(name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Member10{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
