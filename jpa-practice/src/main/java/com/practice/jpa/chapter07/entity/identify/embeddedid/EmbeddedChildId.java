package com.practice.jpa.chapter07.entity.identify.embeddedid;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmbeddedChildId implements Serializable {
    private String parentId;
    @Column(name = "CHILD_ID")
    private String childId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmbeddedChildId that = (EmbeddedChildId) o;
        return Objects.equals(parentId, that.parentId) && Objects.equals(childId, that.childId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentId, childId);
    }

    @Override
    public String toString() {
        return "EmbeddedChildId{" +
                "parentId='" + parentId + '\'' +
                ", childId='" + childId + '\'' +
                '}';
    }
}
