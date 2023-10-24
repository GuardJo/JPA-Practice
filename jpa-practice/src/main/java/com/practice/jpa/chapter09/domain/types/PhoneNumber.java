package com.practice.jpa.chapter09.domain.types;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.practice.jpa.chapter09.domain.PhoneServiceProvider;

@Embeddable
public class PhoneNumber {
	private String areaCode;
	private String phoneNumber;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "PROVIDER_ID")
	private PhoneServiceProvider serviceProvider;

	protected PhoneNumber() {

	}

	private PhoneNumber(String areaCode, String phoneNumber, PhoneServiceProvider serviceProvider) {
		this.areaCode = areaCode;
		this.phoneNumber = phoneNumber;
		this.serviceProvider = serviceProvider;
	}

	public static PhoneNumber create(String areaCode, String phoneNumber, PhoneServiceProvider serviceProvider) {
		return new PhoneNumber(areaCode, phoneNumber, serviceProvider);
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public PhoneServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setServiceProvider(PhoneServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Override
	public String toString() {
		return "PhoneNumber{" +
			"areaCode='" + areaCode + '\'' +
			", phoneNumber='" + phoneNumber + '\'' +
			", serviceProvider=" + serviceProvider +
			'}';
	}
}
