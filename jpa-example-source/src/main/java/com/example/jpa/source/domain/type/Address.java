package com.example.jpa.source.domain.type;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	@Column(length = 50)
	private String city;
	@Column(length = 50)
	private String street;
	private int zipCode = 0;

	protected Address() {

	}

	private Address(String city, String street, int zipCode) {
		this.city = city;
		this.street = street;
		this.zipCode = zipCode;
	}

	public static Address create(String city, String street, int zipCode) {
		return new Address(city, street, zipCode);
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

	@Override
	public String toString() {
		return "Address{" +
			"city='" + city + '\'' +
			", street='" + street + '\'' +
			", zipCode=" + zipCode +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Address address = (Address)o;
		return zipCode == address.zipCode && Objects.equals(city, address.city) && Objects.equals(street, address.street);
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, street, zipCode);
	}
}
