package com.practice.jpa.chapter09;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.practice.jpa.chapter09.domain.Member9;
import com.practice.jpa.chapter09.domain.PhoneServiceProvider;
import com.practice.jpa.chapter09.domain.types.Address;
import com.practice.jpa.chapter09.domain.types.PhoneNumber;
import com.practice.jpa.chapter09.domain.types.Zipcode;

public class JpaDataTypeExample implements Runnable {
	private final EntityManager entityManager;

	public JpaDataTypeExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		initData();
		printMember();
	}

	private void initData() {
		Member9 member = Member9.create("kyeongho", 27);
		String city = "서울";
		String street = "서울숲길";
		Zipcode zipcode = Zipcode.create("000", "111");
		Address address = Address.create(zipcode, city, street);
		Address companyAddress = Address.create(zipcode, "경기", "대왕판교로");
		PhoneServiceProvider serviceProvider = PhoneServiceProvider.create("U+", 010);
		PhoneNumber phoneNumber = PhoneNumber.create("kr", "010-3333-4444", serviceProvider);
		member.setHomeAddress(address);
		member.setCompanyAddress(companyAddress);
		member.setPhoneNumber(phoneNumber);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(member);
		transaction.commit();
	}

	private void printMember() {
		System.out.println("Member 조회");
		long memberId = 1L;

		Member9 member = entityManager.find(Member9.class, memberId);

		System.out.println(member);
	}
}
