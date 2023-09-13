package com.practice.jpa.chapter06.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CH06_LOCKER")
public class Locker {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String lockerName;
	@OneToOne(mappedBy = "locker")
	private Member6_3 member;

	protected Locker() {

	}

	public Locker(String lockerName) {
		this.lockerName = lockerName;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	protected void setMember(Member6_3 member) {
		this.member = member;
	}

	public Long getId() {
		return id;
	}

	public String getLockerName() {
		return lockerName;
	}

	public Member6_3 getMember() {
		return member;
	}

	@Override
	public String toString() {
		return "Locker{" +
			"id=" + id +
			", lockerName='" + lockerName + '\'' +
			'}';
	}
}
