package com.practice.jpa.chapter07.entity.concreate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CONCREATE_ALBUM")
public class ConcreateAlbum extends ConcreateItem{
    private String artist;

    protected ConcreateAlbum() {

    }
    private ConcreateAlbum(Long id, String name, int price, String artist) {
        super(id, name, price);
        this.artist = artist;
    }

    public static ConcreateAlbum of(Long id, String name, int price, String artist) {
        return new ConcreateAlbum(id, name, price, artist);
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConcreateAlbum that = (ConcreateAlbum) o;
        return Objects.equals(artist, that.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), artist);
    }
}
