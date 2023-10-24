package com.practice.jpa.chapter09;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.practice.jpa.chapter09.domain.Member9;
import com.practice.jpa.chapter09.domain.Member9_2;
import com.practice.jpa.chapter09.domain.Period9;
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

		initCollections();
		printMemberWithCollectionTable();
		updateMemberWithCollectionTable();
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

	private void initCollections() {
		Member9_2 member = Member9_2.create("kyeongho2");
		member.getFavoriteFood().add("탕수육");
		member.getFavoriteFood().add("피자");

		member.getPeriods().add(Period9.create());

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

	private void printMemberWithCollectionTable() {
		entityManager.clear();
		System.out.println("CollectionTable이 포함된 Member 조회");
		long memberId = 1L;

		Member9_2 member = entityManager.find(Member9_2.class, memberId);

		System.out.println(member);
	}

	private void updateMemberWithCollectionTable() {
		entityManager.clear();
		System.out.println("CollectionTable이 포함된 Member 갱신");
		long memberId = 1L;

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		Member9_2 member = entityManager.find(Member9_2.class, memberId);
		List<Period9> periods = new ArrayList<>();
		periods.add(Period9.create());

		member.setPeriods(periods);

		transaction.commit();
	}
}
