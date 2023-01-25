package com.practice.jpa.data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "BOARD")
public class Board {
    private Long id;
    @Column(nullable = false)
    private String title;
    private String content;

    @Column(nullable = false)
    private String creator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatTime;

    protected Board() {}
    private Board(Long id, String title, String content, String creator, Date creatTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.creatTime = creatTime;
    }

    public static Board of(String title, String content, String creator, Date creatTime) {
        return new Board(null, title, content, creator, creatTime);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return id.equals(board.id) && title.equals(board.title) && Objects.equals(content, board.content) && creator.equals(board.creator) && creatTime.equals(board.creatTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, creator, creatTime);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creator='" + creator + '\'' +
                ", creatTime=" + creatTime +
                '}';
    }
}
