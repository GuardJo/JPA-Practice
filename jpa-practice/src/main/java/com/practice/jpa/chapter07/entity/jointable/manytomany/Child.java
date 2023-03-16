package com.practice.jpa.chapter07.entity.jointable.manytomany;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ManyToManyChild")
@Table(name = "MANY_TO_MANY_CHILD")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_ID")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "children")
    private Set<Parent> parents = new HashSet<>();

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

    public Set<Parent> getParents() {
        return parents;
    }

    public void setParents(Set<Parent> parents) {
        this.parents = parents;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
