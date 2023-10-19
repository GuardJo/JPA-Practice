package com.practice.jpa.chapter09.domain.types;

import javax.persistence.Embeddable;

@Embeddable
public class Zipcode {
	private String zip;
	private String code;

	protected Zipcode() {

	}

	private Zipcode(String zip, String code) {
		this.zip = zip;
		this.code = code;
	}

	public static Zipcode create(String zip, String code) {
		return new Zipcode(zip, code);
	}

	public String getZip() {
		return zip;
	}

	public String getCode() {
		return code;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Zipcode{" +
			"zip='" + zip + '\'' +
			", code='" + code + '\'' +
			'}';
	}
}
