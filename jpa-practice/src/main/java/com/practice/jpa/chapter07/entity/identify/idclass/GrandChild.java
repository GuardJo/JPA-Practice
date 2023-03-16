package com.practice.jpa.chapter07.entity.identify.idclass;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "IdentifyGrandChild")
@Table(name = "IDENTIFY_GRANDCHILD")
@IdClass(GrandChildId.class)
public class GrandChild {
    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID"),
            @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    })
    private Child child;
    @Id
    @Column(name = "GRANDCHILD_ID")
    private String grandChildId;
    private String name;

    public String getGrandChildId() {
        return grandChildId;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public void setGrandChildId(String grandChildId) {
        this.grandChildId = grandChildId;
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
        GrandChild that = (GrandChild) o;
        return Objects.equals(child, that.child) && Objects.equals(grandChildId, that.grandChildId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(child, grandChildId, name);
    }

    @Override
    public String toString() {
        return "GrandChild{" +
                "child=" + child +
                ", grandChildId='" + grandChildId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
