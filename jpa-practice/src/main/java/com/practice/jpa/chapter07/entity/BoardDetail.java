package com.practice.jpa.chapter07.entity;

import javax.persistence.*;

@Entity
@Table(name = "BOARD_DETAIL")
public class BoardDetail {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BoardDetail{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
