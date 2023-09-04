package com.practice.jpa.chapter07.entity.identify.embeddedid;

import javax.persistence.*;

@Entity(name = "IdentifyEmbeddedChild")
@Table(name = "INDENTIFY_EMBEDDED_CHILD")
public class EmbeddedChild {
    @EmbeddedId
    private EmbeddedChildId childId;

    @MapsId("parentId")
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private EmbeddedParent parent;
    private String name;

    public EmbeddedChildId getChildId() {
        return childId;
    }

    public void setChildId(EmbeddedChildId childId) {
        this.childId = childId;
    }

    public EmbeddedParent getParent() {
        return parent;
    }

    public void setParent(EmbeddedParent parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmbeddedChild{" +
                "childId=" + childId +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                '}';
    }
}
