package com.practice.jpa.chapter10.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH10_TEAM")
public class Team10 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany(mappedBy = "team")
	private List<Member10_2> members = new ArrayList<>();

	protected Team10() {

	}

	private Team10(String name) {
		this.name = name;
	}

	public static Team10 create(String name) {
		return new Team10(name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Member10_2> getMembers() {
		return members;
	}

	@Override
	public String toString() {
		return "Team10{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
