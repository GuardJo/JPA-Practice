package com.practice.jpa.chapter09.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CH09_PHONE_SERVICE_PROVIDER")
public class PhoneServiceProvider {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int provideNumber;

	protected PhoneServiceProvider() {

	}

	private PhoneServiceProvider(String name, int provideNumber) {
		this.name = name;
		this.provideNumber = provideNumber;
	}

	public static PhoneServiceProvider create(String name, int provideNumber) {
		return new PhoneServiceProvider(name, provideNumber);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getProvideNumber() {
		return provideNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvideNumber(int provideNumber) {
		this.provideNumber = provideNumber;
	}

	@Override
	public String toString() {
		return "PhoneServiceProvider{" +
			"id=" + id +
			", name='" + name + '\'' +
			", provideNumber=" + provideNumber +
			'}';
	}
}
