package com.example.jpa.source.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.jpa.source.domain.type.Address;

@Entity
@Table(name = "DELIVERY")
public class Delivery extends MetaData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DELIVERY_ID")
	private Long id;
	@Embedded
	private Address address;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private DeliveryStatus status = DeliveryStatus.PREPARING;

	protected Delivery() {

	}

	private Delivery(String city, String street, int zipCode) {
		this.address = Address.create(city, street, zipCode);
		this.createdDate = LocalDateTime.now();
		this.modifiedDate = LocalDateTime.now();
	}

	public static Delivery of(String city, String street, int zipCode) {
		return new Delivery(city, street, zipCode);
	}

	public Long getId() {
		return id;
	}

	public Address getAddress() {
		return address;
	}

	public DeliveryStatus getStatus() {
		return status;
	}

	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Delivery{" +
			"id=" + id +
			", address=" + address +
			", status=" + status +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}
}
