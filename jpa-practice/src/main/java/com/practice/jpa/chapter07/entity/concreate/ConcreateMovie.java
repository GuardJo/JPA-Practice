package com.practice.jpa.chapter07.entity.concreate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CONCREATE_MOVIE")
public class ConcreateMovie extends ConcreateItem{
    private String director;
    private String actor;

    protected ConcreateMovie() {

    }

    private ConcreateMovie(Long id, String name, int price, String director, String actor) {
        super(id, name, price);
        this.director = director;
        this.actor = actor;
    }

    public static ConcreateMovie of(Long id, String name, int price, String director, String actor) {
        return new ConcreateMovie(id, name, price, director, actor);
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConcreateMovie that = (ConcreateMovie) o;
        return Objects.equals(director, that.director) && Objects.equals(actor, that.actor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), director, actor);
    }
}
