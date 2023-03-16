package com.practice.jpa.chapter07.entity.jointable.onetoone;

import javax.persistence.*;

@Entity(name = "OneToOneParent")
@Table(name = "ONE_TO_ONE_PARENT")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARENT_ID")
    private Long id;
    private String name;
    @OneToOne
    @JoinTable(
            name = "ONE_TO_ONE_PARENT_CHILD",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID")
    )
    private Child child;

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

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", child=" + child +
                '}';
    }
}
