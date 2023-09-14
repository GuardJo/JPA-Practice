package com.practice.jpa.chapter06.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_MEMBER_3")
public class Member6_3 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	@OneToOne(mappedBy = "member")
	private Locker locker;

	protected Member6_3() {

	}

	public Member6_3(String username) {
		this.username = username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	protected void setLocker(Locker locker) {
		this.locker = locker;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Locker getLocker() {
		return locker;
	}

	@Override
	public String toString() {
		return "Member6_3{" +
			"id=" + id +
			", username='" + username + '\'' +
			'}';
	}
}
