package com.practice.jpa.chapter07.entity.nonidentify;

import javax.persistence.*;

@Entity(name = "NewGrandChild")
@Table(name = "NEW_GRANDCHILD")
public class GrandChild {
    @Id
    @Column(name = "GRANDCHILD_ID")
    private String id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "CHILD_ID")
    private Child child;

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

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "GrandChild{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", child=" + child +
                '}';
    }
}
