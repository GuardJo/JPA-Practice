package com.practice.jpa.chapter05.data;

import javax.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "OLD_PERSON")
public class OldPerson {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERSON_ID")
	private Long id;
	@Column(length = 255)
	private String name;
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		if (this.team != null) {
			this.team.getPersonList().remove(this);
		}
		this.team = team;

		if (team != null) {
			this.team.getPersonList().add(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OldPerson oldPerson = (OldPerson)o;
		return Objects.equals(id, oldPerson.id) && Objects.equals(name, oldPerson.name) && Objects.equals(team, oldPerson.team);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, team);
	}

	@Override
	public String toString() {
		return "Person{" +
			"id=" + id +
			", name='" + name + '\'' +
			", team=" + team +
			'}';
	}

	protected OldPerson() {

	}

	private OldPerson(String name) {
		this.name = name;
	}

	public static OldPerson of(String name) {
		return new OldPerson(name);
	}
}
