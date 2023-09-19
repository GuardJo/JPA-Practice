package com.practice.jpa.chapter06.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_MEMBER_5")
public class Member6_5 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;

	@OneToMany(mappedBy = "member")
	private List<MemberProduct> memberProducts = new ArrayList<>();

	protected Member6_5() {

	}

	public Member6_5(String username) {
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

	public List<MemberProduct> getMemberProducts() {
		return memberProducts;
	}

	@Override
	public String toString() {
		return "Member6_4{" +
			"id=" + id +
			", username='" + username + '\'' +
			'}';
	}
}
