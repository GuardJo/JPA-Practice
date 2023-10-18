package com.practice.jpa.chapter09.domain.types;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	private String zipcode;
	private String city;
	private String street;

	protected Address() {

	}

	private Address(String zipcode, String city, String street) {
		this.zipcode = zipcode;
		this.city = city;
		this.street = street;
	}

	public static Address create(String zipcode, String city, String street) {
		return new Address(zipcode, city, street);
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	@Override
	public String toString() {
		return "Address{" +
			"zipcode='" + zipcode + '\'' +
			", city='" + city + '\'' +
			", street='" + street + '\'' +
			'}';
	}
}
