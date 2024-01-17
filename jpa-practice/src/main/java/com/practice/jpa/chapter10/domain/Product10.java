package com.practice.jpa.chapter10.domain;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@Entity
@Table(name = "CH10_PRODUCT")
@SqlResultSetMapping(
	name = "entityWithTotalCount",
	entities = {@EntityResult(entityClass = Product10.class)},
	columns = {@ColumnResult(name = "total_product")}
)
public class Product10 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int price;
	private int stockAmount;

	protected Product10() {

	}

	private Product10(String name, int price, int stockAmount) {
		this.name = name;
		this.price = price;
		this.stockAmount = stockAmount;
	}

	public static Product10 create(String name, int price, int stockAmount) {
		return new Product10(name, price, stockAmount);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getStockAmount() {
		return stockAmount;
	}

	@Override
	public String toString() {
		return "Product10{" +
			"id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", stockAmount=" + stockAmount +
			'}';
	}
}
