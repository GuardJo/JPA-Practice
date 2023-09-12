package com.practice.jpa.chapter06.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_MEMBER")
public class Member6 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;

	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team6 team;

	protected Member6() {

	}

	public Member6(String username) {
		this.username = username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTeam(Team6 team) {
		this.team = team;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Team6 getTeam() {
		return team;
	}

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", username='" + username + '\'' +
			", team=" + team +
			'}';
	}
}
