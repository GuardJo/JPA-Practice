package com.practice.jpa.chapter10.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CH10_MEMBER_2")
public class Member10_2 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private int age;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "TEAM_ID")
	private Team10 team;

	protected Member10_2() {

	}

	private Member10_2(String username, int age) {
		this.username = username;
		this.age = age;
	}

	public static Member10_2 create(String username, int age) {
		return new Member10_2(username, age);
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public int getAge() {
		return age;
	}

	public Team10 getTeam() {
		return team;
	}

	public void setTeam(Team10 team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Member10_2{" +
			"id=" + id +
			", username='" + username + '\'' +
			", age=" + age +
			", team=" + team +
			'}';
	}
}
