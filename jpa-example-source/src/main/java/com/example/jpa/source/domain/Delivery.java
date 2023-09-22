package com.example.jpa.source.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DELIVERY")
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DELIVERY_ID")
	private Long id;
	@Column(length = 50)
	private String city;
	@Column(length = 50)
	private String street;
	private int zipCode = 0;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private DeliveryStatus status = DeliveryStatus.PREPARING;

	protected Delivery() {

	}

	private Delivery(String city, String street, int zipCode) {
		this.city = city;
		this.street = street;
		this.zipCode = zipCode;
	}

	public static Delivery of(String city, String street, int zipCode) {
		return new Delivery(city, street, zipCode);
	}

	public Long getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public int getZipCode() {
		return zipCode;
	}

	public DeliveryStatus getStatus() {
		return status;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Delivery{" +
			"id=" + id +
			", city='" + city + '\'' +
			", street='" + street + '\'' +
			", zipCode=" + zipCode +
			", status=" + status +
			'}';
	}
}
