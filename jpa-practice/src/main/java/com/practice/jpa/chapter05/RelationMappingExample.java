package com.practice.jpa.chapter05;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.practice.jpa.chapter05.data.OldPerson;
import com.practice.jpa.chapter05.data.Team;

public class RelationMappingExample implements Runnable {
	private final EntityManagerFactory entityManagerFactory;

	public RelationMappingExample(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public void run() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		System.out.println("연관관계 매핑 기초");

		OldPerson oldPerson = persistPersonAndTeam(Team.of("testTeam"), entityManager);

		System.out.println(oldPerson.toString());

		readPersonAndTeamWithClassGraph(oldPerson.getId(), entityManager);
		readPersonAndTeamWithJpql(oldPerson.getId(), entityManager);
		readTeamAndPerson(oldPerson.getTeam().getId(), entityManager);

		updateRelation(Team.of("newTeam"), entityManager, oldPerson.getId());

		removeTeam(oldPerson, entityManager);

		endOfRelationOfTeam(oldPerson.getId(), entityManager);

		saveNonMember(entityManager);

		saveMemberWithTeam(entityManager);

		saveMemberWthTeamChange(entityManager);

		entityManager.close();
	}

	/**
	 * 연관 관계 매핑 설정 테스트
	 *
	 * @param team          연관 관계를 설정할 Entity 객체
	 * @param entityManager 엔티티 매니저
	 * @return 연관 관계를 지닌 Person 영속 객체 반환
	 */
	private OldPerson persistPersonAndTeam(Team team, EntityManager entityManager) {
		System.out.println("N : 1 관계 매핑");
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(team);

		OldPerson oldPerson = OldPerson.of("tester");
		oldPerson.setTeam(team);
		entityManager.persist(oldPerson);

		transaction.commit();

		return oldPerson;
	}

	/**
	 * 연관 관계 설정된 Entity 객체 수정 테스트
	 *
	 * @param team          변경할 연관 Entity 객체
	 * @param entityManager 엔티티 매니저
	 * @param personId      수정할 Person 객체 식별값
	 * @return 수정된 Person 영속 객체
	 */
	private OldPerson updateRelation(Team team, EntityManager entityManager, long personId) {
		System.out.println("연관된 Entity 객체 갱신 테스트");
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(team);

		OldPerson oldPerson = entityManager.find(OldPerson.class, personId);
		oldPerson.setTeam(team);

		transaction.commit();

		return oldPerson;
	}

	/**
	 * 연관 관계에 따른 객체 그래프 탐색
	 * @param personId 영속화된 Person Entity 식별값
	 */
	private void readPersonAndTeamWithClassGraph(Long personId, EntityManager entityManager) {
		System.out.println("객체 그래프 탐색 테스트");
		OldPerson oldPerson = entityManager.find(OldPerson.class, personId);
		Team team = oldPerson.getTeam();

		System.out.println(oldPerson.toString());
		System.out.println(team.toString());
	}

	/**
	 * 연관 관계에 따른 JPQL 탐색
	 * @param personId 영속화된 Person Entity 식별값
	 */
	private void readPersonAndTeamWithJpql(Long personId, EntityManager entityManager) {
		System.out.println("JPQL 탐색 테스트");

		String jpql = "select p from OldPerson p join p.team t where t.name=:teamName";

		List<OldPerson> oldPersonList = entityManager.createQuery(jpql, OldPerson.class)
			.setParameter("teamName", "testTeam")
			.getResultList();

		for (OldPerson oldPerson : oldPersonList) {
			System.out.println(oldPerson.toString());
		}
	}

	/**
	 * 양방향 연관관계 설정을 통해 Team 객체에 연관된 Person 객체 순회
	 * @param teamId
	 * @param entityManager
	 */
	private void readTeamAndPerson(Long teamId, EntityManager entityManager) {
		System.out.println("양방향성 테스트 (Team -> Person)");

		Team team = entityManager.find(Team.class, teamId);

		List<OldPerson> oldPersonList = team.getPersonList();

		for (OldPerson oldPerson : oldPersonList) {
			System.out.println(oldPerson.toString());
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
		OldPerson oldPerson = entityManager.find(OldPerson.class, personId);
		oldPerson.setTeam(null);

		transaction.commit();
	}

	/**
	 * 연관관계에 걸려있는 테이블 제거
	 * @param entityManager
	 */
	private void removeTeam(OldPerson oldPerson, EntityManager entityManager) {
		System.out.println("연관관계에 걸려있는 테이블 제거");

		Team team = oldPerson.getTeam();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		oldPerson.setTeam(null);
		entityManager.remove(team);
		transaction.commit();
	}

	/**
	 * 양방향 매핑 시 주인에 해당하는 Entity는 갱신하지 않고 반대편만 갱신하는 경우
	 * @param entityManager
	 */
	private void saveNonMember(EntityManager entityManager) {
		System.out.println("양방향 매핑 시 주인이 아닌 쪽만 업데이트 하는 경우");

		OldPerson oldPerson = OldPerson.of("tester");
		Team team = Team.of("Test22");
		team.getPersonList().add(oldPerson);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(team);
		entityManager.flush();

		transaction.commit();
	}

	/**
	 * 양방향 매핑을 통해 양쪽에서 데이터를 조회할 수 있도록 구성
	 * 단, 연관관계의 주인은 Person Entity이기에 해당 Entity에 관계 관련 값을 갱신해야함
	 * @param entityManager
	 */
	private void saveMemberWithTeam(EntityManager entityManager) {
		System.out.println("양방향 매핑을 통한 데이터 조회");
		OldPerson oldPerson = OldPerson.of("tester");
		Team team = Team.of("test33");

		oldPerson.setTeam(team);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(team);
		entityManager.persist(oldPerson);

		OldPerson saveOldPerson = entityManager.find(OldPerson.class, oldPerson.getId());
		Team saveTeam = entityManager.find(Team.class, team.getId());

		System.out.println(saveOldPerson.toString());
		System.out.println(saveTeam.toString());
		saveTeam.getPersonList()
			.forEach(oldPerson1 -> System.out.println(oldPerson1.toString()));

		transaction.commit();
	}

	/**
	 * 양방향 매핑 관계에서 양쪽 관계 설정 후 다른 객체와 변경 테스트
	 * @param entityManager
	 */
	private void saveMemberWthTeamChange(EntityManager entityManager) {
		System.out.println("양방향 매핑 요소 변경 테스트");
		OldPerson oldPerson = OldPerson.of("tester");
		Team team = Team.of("test33");
		Team changeTeam = Team.of("change");

		oldPerson.setTeam(team);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(team);
		entityManager.persist(oldPerson);
		entityManager.persist(changeTeam);

		oldPerson.setTeam(changeTeam);

		OldPerson saveOldPerson = entityManager.find(OldPerson.class, oldPerson.getId());
		Team saveTeam = entityManager.find(Team.class, team.getId());
		Team saveChangeTeam = entityManager.find(Team.class, changeTeam.getId());

		System.out.println(saveOldPerson.toString());
		System.out.println(saveTeam.toString());
		saveTeam.getPersonList()
			.forEach(oldPerson1 -> System.out.println(oldPerson1.toString()));
		System.out.println(saveChangeTeam.toString());
		saveChangeTeam.getPersonList()
			.forEach(oldPerson1 -> System.out.println(oldPerson1.toString()));

		transaction.commit();
	}

}
