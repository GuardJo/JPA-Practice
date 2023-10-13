package com.practice.jpa.chapter08.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH08_MEMBER")
public class Member8 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEAM_ID")
	private Team8 team;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Order8> orders = new ArrayList<>();

	protected Member8() {

	}

	private Member8(String name) {
		this.name = name;
	}

	public static Member8 of(String name) {
		return new Member8(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTeam(Team8 team) {
		this.team = team;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Team8 getTeam() {
		return team;
	}

	public List<Order8> getOrders() {
		return orders;
	}

	@Override
	public String toString() {
		return "Member8{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
