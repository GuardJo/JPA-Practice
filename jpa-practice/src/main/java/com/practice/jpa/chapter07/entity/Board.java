package com.practice.jpa.chapter07.entity;

import javax.persistence.*;

@Entity(name = "NewBoard")
@Table(name = "NEW_BOARD")
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;
    private String title;

    @OneToOne(mappedBy = "board")
    private BoardDetail boardDetail;

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

    public BoardDetail getBoardDetail() {
        return boardDetail;
    }

    public void setBoardDetail(BoardDetail boardDetail) {
        this.boardDetail = boardDetail;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", boardDetail=" + boardDetail +
                '}';
    }
}
