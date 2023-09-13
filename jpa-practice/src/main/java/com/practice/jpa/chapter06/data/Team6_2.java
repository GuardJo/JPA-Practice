package com.practice.jpa.chapter06.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_TEAM_2")
public class Team6_2 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToMany
	@JoinColumn(name = "TEAM_ID")
	private List<Member6_2> members = new ArrayList<>();

	protected Team6_2() {

	}

	public Team6_2(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMembers(List<Member6_2> members) {
		this.members = members;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Member6_2> getMembers() {
		return members;
	}

	@Override
	public String toString() {
		return "Team6_2{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
