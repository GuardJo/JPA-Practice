package com.practice.jpa.chapter07.entity.nonidentify;

import javax.persistence.*;

@Entity(name = "NewChild")
@Table(name = "NEW_CHILD")
public class Child {
    @Id
    @Column(name = "CHILD_ID")
    private String id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                '}';
    }
}
