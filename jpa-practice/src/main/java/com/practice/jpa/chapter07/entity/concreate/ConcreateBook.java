package com.practice.jpa.chapter07.entity.concreate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CONCREATE_BOOK")
public class ConcreateBook extends ConcreateItem{
    private String author;
    private String isbn;

    protected ConcreateBook() {

    }

    private ConcreateBook(Long id, String name, int price, String author, String isbn) {
        super(id, name, price);
        this.author = author;
        this.isbn = isbn;
    }

    public static ConcreateBook of(Long id, String name, int price, String author, String isbn) {
        return new ConcreateBook(id, name, price, author, isbn);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConcreateBook that = (ConcreateBook) o;
        return Objects.equals(author, that.author) && Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, isbn);
    }
}
