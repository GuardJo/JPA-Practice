package com.practice.jpa.chapter08.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CH08_TEAM")
public class Team8 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	protected Team8() {

	}

	private Team8(String name) {
		this.name = name;
	}

	public static Team8 of(String name) {
		return new Team8(name);
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

	@Override
	public String toString() {
		return "Team8{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
