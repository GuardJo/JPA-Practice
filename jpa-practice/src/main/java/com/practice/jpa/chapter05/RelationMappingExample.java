package com.practice.jpa.chapter05;

import com.practice.jpa.chapter05.data.Person;
import com.practice.jpa.chapter05.data.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RelationMappingExample implements Runnable {
    private final EntityManagerFactory entityManagerFactory;

    public RelationMappingExample(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("연관관계 매핑 기초");

        Person person = persistPersonAndTeam(Team.of("testTeam"), entityManager);

        System.out.println(person.toString());

        readPersonAndTeamWithClassGraph(person.getId(), entityManager);
        readPersonAndTeamWithJpql(person.getId(), entityManager);

        updateRelation(Team.of("newTeam"), entityManager, person.getId());

        removeTeam(person, entityManager);

        endOfRelationOfTeam(person.getId(), entityManager);

        entityManager.close();
    }

    /**
     * 연관 관계 매핑 설정 테스트
     *
     * @param team          연관 관계를 설정할 Entity 객체
     * @param entityManager 엔티티 매니저
     * @return 연관 관계를 지닌 Person 영속 객체 반환
     */
    private Person persistPersonAndTeam(Team team, EntityManager entityManager) {
        System.out.println("N : 1 관계 매핑");
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(team);

        Person person = Person.of("tester");
        person.setTeam(team);
        entityManager.persist(person);

        transaction.commit();

        return person;
    }

    /**
     * 연관 관계 설정된 Entity 객체 수정 테스트
     *
     * @param team          변경할 연관 Entity 객체
     * @param entityManager 엔티티 매니저
     * @param personId      수정할 Person 객체 식별값
     * @return 수정된 Person 영속 객체
     */
    private Person updateRelation(Team team, EntityManager entityManager, long personId) {
        System.out.println("연관된 Entity 객체 갱신 테스트");
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(team);

        Person person = entityManager.find(Person.class, personId);
        person.setTeam(team);

        transaction.commit();

        return person;
    }

    /**
     * 연관 관계에 따른 객체 그래프 탐색
     * @param personId 영속화된 Person Entity 식별값
     */
    private void readPersonAndTeamWithClassGraph(Long personId, EntityManager entityManager) {
        System.out.println("객체 그래프 탐색 테스트");
        Person person = entityManager.find(Person.class, personId);
        Team team = person.getTeam();

        System.out.println(person.toString());
        System.out.println(team.toString());
    }

    /**
     * 연관 관계에 따른 JPQL 탐색
     * @param personId 영속화된 Person Entity 식별값
     */
    private void readPersonAndTeamWithJpql(Long personId, EntityManager entityManager) {
        System.out.println("JPQL 탐색 테스트");
        
        String jpql = "select p from Person p join p.team t where t.name=:teamName";

        List<Person> personList = entityManager.createQuery(jpql, Person.class)
                .setParameter("teamName", "testTeam")
                .getResultList();

        for (Person person : personList) {
            System.out.println(person.toString());
        }
    }

    /**
     * 연관관계 제거
     * @param personId 연관관계를 지니고 있는 Entity 객체 식별값
     * @param entityManager Entity Manager
     */
    private void endOfRelationOfTeam(Long personId, EntityManager entityManager) {
        System.out.println("연관관계 제거 테스트");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Person person = entityManager.find(Person.class, personId);
        person.setTeam(null);

        transaction.commit();
    }

    /**
     * 연관관계에 걸려있는 테이블 제거
     * @param entityManager
     */
    private void removeTeam(Person person, EntityManager entityManager) {
        System.out.println("연관관계에 걸려있는 테이블 제거");

        Team team = person.getTeam();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        person.setTeam(null);
        entityManager.remove(team);
        transaction.commit();
    }
}
