package com.practice.jpa.chapter09.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CH09_MEMBER")
public class Member9 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int age;

	protected Member9() {

	}

	private Member9(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public static Member9 create(String name, int age) {
		return new Member9(name, age);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Member9{" +
			"id=" + id +
			", name='" + name + '\'' +
			", age=" + age +
			'}';
	}
}
