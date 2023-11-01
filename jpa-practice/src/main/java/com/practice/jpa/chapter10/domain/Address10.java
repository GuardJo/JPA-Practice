package com.practice.jpa.chapter10.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address10 {
	private String city;
	private String street;
	private String zipcode;

	protected Address10() {

	}

	private Address10(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

	public static Address10 create(String city, String street, String zipcode) {
		return new Address10(city, street, zipcode);
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

	@Override
	public String toString() {
		return "Address10{" +
			"city='" + city + '\'' +
			", street='" + street + '\'' +
			", zipcode='" + zipcode + '\'' +
			'}';
	}
}
