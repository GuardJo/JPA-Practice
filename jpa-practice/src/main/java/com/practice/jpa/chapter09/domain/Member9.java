package com.practice.jpa.chapter09.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.practice.jpa.chapter09.domain.types.Address;
import com.practice.jpa.chapter09.domain.types.Period;
import com.practice.jpa.chapter09.domain.types.PhoneNumber;

@Entity
@Table(name = "CH09_MEMBER")
public class Member9 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int age;

	// 근무 기간
	@Embedded
	private Period workPeriod;

	// 집 주소
	@Embedded
	private Address homeAddress;
	// 회사 주소
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "zipcode.zip", column = @Column(name = "COMPANY_ZIP")),
		@AttributeOverride(name = "zipcode.code", column = @Column(name = "COMPANY_CODE")),
		@AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
		@AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET"))
	})
	private Address companyAddress;

	// 휴대폰 번호
	@Embedded
	private PhoneNumber phoneNumber;

	protected Member9() {

	}

	private Member9(String name, int age) {
		this.name = name;
		this.age = age;
		this.workPeriod = Period.create();
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
		return workPeriod.getStartDate();
	}

	public Date getFinishedDate() {
		return workPeriod.getFinishedDate();
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public Period getWorkPeriod() {
		return workPeriod;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public Address getCompanyAddress() {
		return companyAddress;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public void setCompanyAddress(Address companyAddress) {
		this.companyAddress = companyAddress;
	}

	public void setWorkPeriod(Period workPeriod) {
		this.workPeriod = workPeriod;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Member9{" +
			"id=" + id +
			", name='" + name + '\'' +
			", age=" + age +
			", workPeriod=" + workPeriod +
			", homeAddress=" + homeAddress +
			", companyAddress=" + companyAddress +
			", phoneNumber=" + phoneNumber +
			'}';
	}
}
