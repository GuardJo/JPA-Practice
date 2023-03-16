package com.practice.jpa.chapter07.entity.identify.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "IdentifyParent")
@Table(name = "IDENTIFY_PARENT")
public class Parent {
    @Id
    @Column(name = "PARENT_ID")
    private String parentId;
    private String name;

    public Parent() {

    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
        return Objects.equals(parentId, parent.parentId) && Objects.equals(name, parent.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentId, name);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
