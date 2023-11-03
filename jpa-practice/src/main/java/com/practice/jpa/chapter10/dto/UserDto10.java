package com.practice.jpa.chapter10.dto;

public class UserDto10 {
	private String name;
	private int age;

	public UserDto10(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "UserDto10{" +
			"name='" + name + '\'' +
			", age=" + age +
			'}';
	}
}