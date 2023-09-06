package com.example.jpa.source;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JPA를 활용한 데이터 설계 및 구현 실습용 프로젝트
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello JPA!");

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-example");

		EntityService entityService = new EntityService(entityManagerFactory);

		entityService.run();
	}
}
