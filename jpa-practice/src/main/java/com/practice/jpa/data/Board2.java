package com.practice.jpa.data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "BOARD2")
@SequenceGenerator(
        name = "BOARD_SEQ",
        sequenceName = "BOARD_SEQ",
        initialValue = 1, allocationSize = 1
)
public class Board2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ")
    private Long id;
    @Column(nullable = false)
    private String title;
    private String content;

    @Column(nullable = false)
    private String creator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatTime;

    protected Board2() {}

    private Board2(Long id, String title, String content, String creator) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.creatTime = new Date();
    }

    public static Board2 of(String title, String content, String creator) {
        return new Board2(null, title, content, creator);
    }

    public Long getId() {
        return id;
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
        Board2 board2 = (Board2) o;
        return id.equals(board2.id) && title.equals(board2.title) && Objects.equals(content, board2.content) && creator.equals(board2.creator) && creatTime.equals(board2.creatTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, creator, creatTime);
    }

    @Override
    public String toString() {
        return "Board2{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creator='" + creator + '\'' +
                ", creatTime=" + creatTime +
                '}';
    }
}
