package com.practice.jpa.chapter06.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_MEMBER_2")
public class Member6_2 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;

	protected Member6_2() {

	}

	public Member6_2(String username) {
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

	@Override
	public String toString() {
		return "Member6_2{" +
			"id=" + id +
			", username='" + username + '\'' +
			'}';
	}
}
