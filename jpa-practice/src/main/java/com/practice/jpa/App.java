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
import com.practice.jpa.chapter08.DataLoadExample;
import com.practice.jpa.chapter09.JpaDataTypeExample;
import com.practice.jpa.chapter10.CriteriaExample;
import com.practice.jpa.chapter10.JpqlMultiplotSearchExample;
import com.practice.jpa.chapter10.JpqlSearchExample;
import com.practice.jpa.chapter10.NativeQueryExample;
import com.practice.jpa.chapter10.QueryDslExample;
import com.practice.jpa.chapter10.QuerySearchExample;

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
		DataLoadExample dataLoadExample = new DataLoadExample(entityManagerFactory);
		JpaDataTypeExample jpaDataTypeExample = new JpaDataTypeExample(entityManagerFactory);
		QuerySearchExample querySearchExample = new QuerySearchExample(entityManagerFactory);
		JpqlSearchExample jpqlSearchExample = new JpqlSearchExample(entityManagerFactory);
		JpqlMultiplotSearchExample jpqlMultiplotSearchExample = new JpqlMultiplotSearchExample(entityManagerFactory);
		CriteriaExample criteriaExample = new CriteriaExample(entityManagerFactory);
		QueryDslExample queryDslExample = new QueryDslExample(entityManagerFactory);
		NativeQueryExample nativeQueryExample = new NativeQueryExample(entityManagerFactory);

		//        jpaExample.run();
		//        entityLifecycleExample.run();
		//        dirtyCheckingExample.run();
		//        mergeExample.run();
		//        idGenerationExample.run();
		// relationMappingExample.run();
		// relationshipMappingExample.run();
		// multiIdExample.run();
		// dataLoadExample.run();
		// jpaDataTypeExample.run();
		querySearchExample.run();
		jpqlSearchExample.run();
		jpqlMultiplotSearchExample.run();
		criteriaExample.run();
		queryDslExample.run();
		nativeQueryExample.run();
		
		entityManagerFactory.close();
	}
}
