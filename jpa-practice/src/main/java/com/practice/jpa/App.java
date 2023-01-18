package com.practice.jpa;

import com.practice.jpa.chapter02.example.JpaExample;
import com.practice.jpa.chapter03.example.DirtyCheckingExample;
import com.practice.jpa.chapter03.example.EntityLifecycleExample;
import com.practice.jpa.chapter03.example.MergeExample;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

        jpaExample.run();
        entityLifecycleExample.run();
        dirtyCheckingExample.run();
        mergeExample.run();

        entityManagerFactory.close();
    }
}
