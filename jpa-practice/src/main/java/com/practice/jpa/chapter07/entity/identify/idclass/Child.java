package com.practice.jpa.chapter07.entity.identify.idclass;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "IdentifyChild")
@Table(name = "INDETIFY_CHILD")
@IdClass(ChildId.class)
public class Child {
    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;
    @Id
    @Column(name = "CHILD_ID")
    private String childId;
    private String name;

    public Child() {}

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return Objects.equals(parent, child.parent) && Objects.equals(childId, child.childId) && Objects.equals(name, child.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, childId, name);
    }

    @Override
    public String toString() {
        return "Child{" +
                "parentId=" + parent +
                ", childId='" + childId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
