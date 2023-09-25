package com.example.jpa.source.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 상품 도메인
 * 상품명, 상품 가격, 상품 재고 정보를 지니고 있다
 */
@Entity
@Table(name = "ITEM")
public class Item extends MetaData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITEM_ID")
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int price;
	@Column(nullable = false)
	private int stockQuantity;

	@OneToMany(mappedBy = "item")
	private final List<OrderItem> orderItems = new ArrayList<>();

	@ManyToMany
	@JoinTable(
		name = "CATEGORY_ITEM",
		joinColumns = @JoinColumn(name = "ITEM_ID"),
		inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
	)
	private final List<Category> categories = new ArrayList<>();

	protected Item() {
	}

	private Item(String name, int price, int stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.createdDate = LocalDateTime.now();
		this.modifiedDate = LocalDateTime.now();
	}

	public static Item of(String name, int price, int stockQuantity) {
		return new Item(name, price, stockQuantity);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public List<Category> getCategories() {
		return categories;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Item item = (Item)o;
		return price == item.price && stockQuantity == item.stockQuantity && Objects.equals(id, item.id) && Objects.equals(name, item.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, stockQuantity);
	}

	@Override
	public String toString() {
		return "Item{" +
			"id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", stockQuantity=" + stockQuantity +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}
}
