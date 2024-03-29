package com.practice.jpa.chapter07.entity.nonidentify.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(ParentId.class)
public class Parent {
    @Id
    @Column(name = "PARENT_ID1")
    private String parentId1;
    @Id
    @Column(name = "PARENT_ID2")
    private String parentId2;
    private String name;

    public String getParentId1() {
        return parentId1;
    }

    public void setParentId1(String parentId1) {
        this.parentId1 = parentId1;
    }

    public String getParentId2() {
        return parentId2;
    }

    public void setParentId2(String parentId2) {
        this.parentId2 = parentId2;
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
        Parent parent = (Parent) o;
        return Objects.equals(parentId1, parent.parentId1) && Objects.equals(parentId2, parent.parentId2) && Objects.equals(name, parent.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentId1, parentId2, name);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "parentId1='" + parentId1 + '\'' +
                ", parentId2='" + parentId2 + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
