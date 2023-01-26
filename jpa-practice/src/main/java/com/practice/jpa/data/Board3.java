package com.practice.jpa.data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
@TableGenerator(
        name = "BOARD_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "BOARD_SEQ", allocationSize = 1
)
public class Board3 {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;
    @Column(nullable = false)
    private String title;
    private String content;
    @Column(nullable = false)
    private String creator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    protected Board3() {

    }
    private Board3(String title, String content, String creator) {
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.createTime = new Date();
    }

    public static Board3 of(String title, String content, String creator) {
        return new Board3(title, content, creator);
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

    public Long getId() {
        return id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board3 board3 = (Board3) o;
        return Objects.equals(id, board3.id) && title.equals(board3.title) && content.equals(board3.content) && creator.equals(board3.creator) && Objects.equals(createTime, board3.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, creator, createTime);
    }

    @Override
    public String toString() {
        return "Board3{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
