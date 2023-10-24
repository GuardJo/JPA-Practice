package com.practice.jpa.chapter09.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH09_MEMBER_2")
public class Member9_2 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ElementCollection
	@CollectionTable(
		name = "CH09_FAVORITE_FOODS",
		joinColumns = @JoinColumn(name = "MEMBER_ID")
	)
	private Set<String> favoriteFood = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "MEMBER_ID")
	private List<Period9> periods = new ArrayList<>();

	protected Member9_2() {

	}

	private Member9_2(String name) {
		this.name = name;
	}

	public static Member9_2 create(String name) {
		return new Member9_2(name);
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

	public Set<String> getFavoriteFood() {
		return favoriteFood;
	}

	public List<Period9> getPeriods() {
		return periods;
	}

	public void setFavoriteFood(Set<String> favoriteFood) {
		this.favoriteFood = favoriteFood;
	}

	public void setPeriods(List<Period9> periods) {
		this.periods = periods;
	}

	@Override
	public String toString() {
		return "Member9_2{" +
			"id=" + id +
			", name='" + name + '\'' +
			", favoriteFood=" + favoriteFood +
			", periods=" + periods +
			'}';
	}
}
