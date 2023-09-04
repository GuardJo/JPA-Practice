package com.practice.jpa.chapter07;

import com.practice.jpa.chapter07.entity.nonidentify.idclass.Parent;
import com.practice.jpa.chapter07.entity.nonidentify.idclass.ParentId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class MultiIdExample implements Runnable{
    private final EntityManagerFactory entityManagerFactory;
    public MultiIdExample(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run() {
        String id1 = "id1";
        String id2 = "id2";

        saveMultiIdTable(id1, id2);
        findMultiIdTable(id1, id2);
    }

    private void saveMultiIdTable(String id1, String id2) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        Parent parent = new Parent();
        parent.setParentId1(id1);
        parent.setParentId2(id2);
        parent.setName("test");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(parent);
        transaction.commit();
        entityManager.close();
    }

    private void findMultiIdTable(String id1, String id2) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        ParentId parentId = new ParentId(id1, id2);
        Parent parent = entityManager.find(Parent.class, parentId);

        System.out.println(parent.toString());
    }
}
