package com.practice.jpa.chapter07.entity.jointable.onetoone;

import javax.persistence.*;

@Entity(name = "OneToOneChild")
@Table(name = "ONE_TO_ONE_CHILD")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_ID")
    private Long id;
    private String name;

    @OneToOne(mappedBy = "child")
    private Parent parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
