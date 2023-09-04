package com.practice.jpa.chapter05.data;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TEAM")
public class Team {
	@Id
	@Column(name = "TEAM_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 255)
	private String name;

	@OneToMany(mappedBy = "team")
	private List<OldPerson> oldPersonList = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OldPerson> getPersonList() {
		return oldPersonList;
	}

	public void setPersonList(List<OldPerson> oldPersonList) {
		this.oldPersonList = oldPersonList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Team team = (Team)o;
		return Objects.equals(id, team.id) && Objects.equals(name, team.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "Team{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}

	protected Team() {

	}

	private Team(String name) {
		this.name = name;
	}

	public static Team of(String name) {
		return new Team(name);
	}
}
