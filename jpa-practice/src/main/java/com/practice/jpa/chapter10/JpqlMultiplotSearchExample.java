package com.practice.jpa.chapter10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.practice.jpa.chapter07.entity.join.Album;
import com.practice.jpa.chapter07.entity.join.Book;
import com.practice.jpa.chapter07.entity.join.Item;
import com.practice.jpa.chapter07.entity.join.Movie;
import com.practice.jpa.chapter07.entity.single.SingleAlbum;
import com.practice.jpa.chapter07.entity.single.SingleBook;
import com.practice.jpa.chapter07.entity.single.SingleItem;
import com.practice.jpa.chapter07.entity.single.SingleMovie;

public class JpqlMultiplotSearchExample implements Runnable {
	private final EntityManager entityManager;

	public JpqlMultiplotSearchExample(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void run() {
		initSingleData();
		initJoinTableData();

		findAllDataFromSingleTable();
		findAllDataFromJoined();

		searchTypeFromMultiplotEntities();
		searchFromBookAuthor();

		useCustomFunction();
	}

	private void initSingleData() {
		System.out.println("Init SingleTable Data");

		SingleAlbum singleAlbum = new SingleAlbum();
		singleAlbum.setName("Test Album");
		singleAlbum.setArtist("Artist Jo");
		singleAlbum.setPrice(30000);

		SingleBook singleBook = new SingleBook();
		singleBook.setName("Test Book");
		singleBook.setAuthor("Author Jo");
		singleBook.setIsbn("isbn");
		singleBook.setPrice(20000);

		SingleMovie singleMovie = new SingleMovie();
		singleMovie.setName("Test Movie");
		singleMovie.setPrice(10000);
		singleMovie.setActor("Actor Jo");
		singleMovie.setDirector("Director Jo");

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(singleAlbum);
		entityManager.persist(singleBook);
		entityManager.persist(singleMovie);
		transaction.commit();
	}

	private void initJoinTableData() {
		System.out.println("Init JoinTable Data");

		Album album = Album.of(null, "Test Album", 30000, "Artist Jo");
		Book book = Book.of(null, "Test Book", 20000, "Author Jo", "isbn");
		Movie movie = Movie.of(null, "Test Movie", 10000, "Director Jo", "Actor Jo");

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(album);
		entityManager.persist(book);
		entityManager.persist(movie);
		transaction.commit();
	}

	private void findAllDataFromSingleTable() {
		System.out.println("Find all Multiplot Tables (SingleTable)");

		TypedQuery<SingleItem> query = entityManager.createQuery("select i from SingleItem i", SingleItem.class);
		List<SingleItem> singleItems = query.getResultList();
		singleItems.forEach(System.out::println);
	}

	private void findAllDataFromJoined() {
		System.out.println("Find all Multiplot Tables (Joined)");

		TypedQuery<Item> query = entityManager.createQuery("select i from Item i", Item.class);
		List<Item> items = query.getResultList();
		items.forEach(System.out::println);
	}

	private void searchTypeFromMultiplotEntities() {
		System.out.println("Search All type of Movie and Book from Multiplot Tables");

		TypedQuery<Item> query = entityManager.createQuery("select i from Item i where type (i) in (Book, Movie)", Item.class);
		List<Item> items = query.getResultList();
		items.forEach(System.out::println);
	}

	private void searchFromBookAuthor() {
		System.out.println("Treat 키워드를 사용하여 Author 컬럼에 데이터가 존재하는 Book 데이터 반환");

		TypedQuery<Item> query = entityManager.createQuery("select i from Item i where treat(i as Book).author != null", Item.class);
		List<Item> items = query.getResultList();
		items.forEach(System.out::println);
	}

	private void useCustomFunction() {
		System.out.println("직접 구성한 group_concat 함수를 사용하여 Item 이름 조회");

		TypedQuery<String> query = entityManager.createQuery("select test_concat(i.name) from Item i", String.class);
		List<String> results = query.getResultList();
		results.forEach(System.out::println);
	}
}
