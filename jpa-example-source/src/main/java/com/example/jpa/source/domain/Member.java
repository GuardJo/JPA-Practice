package com.example.jpa.source.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.jpa.source.domain.type.Address;

/**
 * 회원 도메인
 * 회원은 이름, 주소, 가입 일자 정보를 지니고 있다.
 */
@Entity
@Table(name = "MEMBER")
public class Member extends MetaData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;
	@Column(nullable = false)
	private String name;
	@Embedded
	private Address address;
	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date registedDate;

	@OneToMany(mappedBy = "member")
	private final List<Order> orders = new ArrayList<>();

	protected Member() {

	}

	private Member(String name, Address address, Date registedDate) {
		this.name = name;
		this.address = address;
		this.registedDate = registedDate;
		this.createdDate = LocalDateTime.now();
		this.modifiedDate = LocalDateTime.now();
	}

	public static Member of(String name, Address address) {
		return new Member(name, address, new Date());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getRegistedDate() {
		return registedDate;
	}

	public void setRegistedDate(Date registedDate) {
		this.registedDate = registedDate;
	}

	public List<Order> getOrders() {
		return orders;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Member member = (Member)o;
		return Objects.equals(id, member.id) && Objects.equals(name, member.name) && Objects.equals(address, member.address) && Objects.equals(
			registedDate, member.registedDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, address, registedDate);
	}

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", name='" + name + '\'' +
			", address='" + address + '\'' +
			", registedDate=" + registedDate +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}
}
