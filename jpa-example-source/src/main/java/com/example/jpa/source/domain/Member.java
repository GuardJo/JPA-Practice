package com.example.jpa.source.domain;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 회원 도메인
 * 회원은 이름, 주소, 가입 일자 정보를 지니고 있다.
 */
@Entity
@Table(name = "MEMBER")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(length = 500)
	private String address;
	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date registedDate;

	@OneToMany(mappedBy = "member")
	private final List<Order> orders = new ArrayList<>();

	protected Member() {

	}

	private Member(String name, String address, Date registedDate) {
		this.name = name;
		this.address = address;
		this.registedDate = registedDate;
	}

	public static Member of(String name, String address) {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
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
			'}';
	}
}
