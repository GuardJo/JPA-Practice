package com.practice.jpa.chapter07.entity.nonidentify.embeddedid;

import javax.persistence.*;

@Entity
public class EmbeddedChild {
    @Id
    private String childId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID1"),
            @JoinColumn(name = "PARENT_ID2")
    })
    private EmbeddedParent parent;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public EmbeddedParent getParent() {
        return parent;
    }

    public void setParent(EmbeddedParent parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "EmbeddedChild{" +
                "childId='" + childId + '\'' +
                ", parent=" + parent +
                '}';
    }
}
