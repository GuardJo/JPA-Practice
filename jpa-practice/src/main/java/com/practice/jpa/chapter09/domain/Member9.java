package com.practice.jpa.chapter09.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CH09_MEMBER")
public class Member9 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int age;

	// 근무 기간
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date finishedDate;

	// 집 주소
	private String city;
	private String street;
	private String zipcode;

	protected Member9() {

	}

	private Member9(String name, int age) {
		this.name = name;
		this.age = age;
		this.startDate = new Date();
		this.finishedDate = new Date();
	}

	public static Member9 create(String name, int age) {
		return new Member9(name, age);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getFinishedDate() {
		return finishedDate;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "Member9{" +
			"id=" + id +
			", name='" + name + '\'' +
			", age=" + age +
			", startDate=" + startDate +
			", finishedDate=" + finishedDate +
			", city='" + city + '\'' +
			", street='" + street + '\'' +
			", zipcode='" + zipcode + '\'' +
			'}';
	}
}
