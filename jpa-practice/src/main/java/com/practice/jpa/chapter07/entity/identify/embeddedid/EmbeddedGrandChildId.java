package com.practice.jpa.chapter07.entity.identify.embeddedid;

import org.hibernate.annotations.Columns;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmbeddedGrandChildId implements Serializable {
    private EmbeddedChildId childId;
    @Column(name = "GRANDCHILD_ID")
    private String grandChildId;

    public EmbeddedChildId getChildId() {
        return childId;
    }

    public void setChildId(EmbeddedChildId childId) {
        this.childId = childId;
    }

    public String getGrandChildId() {
        return grandChildId;
    }

    public void setGrandChildId(String grandChildId) {
        this.grandChildId = grandChildId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmbeddedGrandChildId that = (EmbeddedGrandChildId) o;
        return Objects.equals(childId, that.childId) && Objects.equals(grandChildId, that.grandChildId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childId, grandChildId);
    }
}
