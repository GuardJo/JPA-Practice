package com.practice.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.practice.jpa.chapter02.JpaExample;
import com.practice.jpa.chapter03.DirtyCheckingExample;
import com.practice.jpa.chapter03.EntityLifecycleExample;
import com.practice.jpa.chapter03.MergeExample;
import com.practice.jpa.chapter04.IdGenerationExample;
import com.practice.jpa.chapter05.RelationMappingExample;
import com.practice.jpa.chapter06.RelationshipMappingExample;
import com.practice.jpa.chapter07.MultiIdExample;

/**
 * Hello JPA!
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello JPA!");

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");

		JpaExample jpaExample = new JpaExample(entityManagerFactory);
		EntityLifecycleExample entityLifecycleExample = new EntityLifecycleExample(entityManagerFactory);
		DirtyCheckingExample dirtyCheckingExample = new DirtyCheckingExample(entityManagerFactory);
		MergeExample mergeExample = new MergeExample(entityManagerFactory);
		IdGenerationExample idGenerationExample = new IdGenerationExample(entityManagerFactory);
		RelationMappingExample relationMappingExample = new RelationMappingExample(entityManagerFactory);
		RelationshipMappingExample relationshipMappingExample = new RelationshipMappingExample(entityManagerFactory);
		MultiIdExample multiIdExample = new MultiIdExample(entityManagerFactory);

		//        jpaExample.run();
		//        entityLifecycleExample.run();
		//        dirtyCheckingExample.run();
		//        mergeExample.run();
		//        idGenerationExample.run();
		// relationMappingExample.run();
		relationshipMappingExample.run();
		// multiIdExample.run();

		entityManagerFactory.close();
	}
}
