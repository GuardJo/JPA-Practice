package com.practice.jpa.chapter07.entity.identify.embeddedid;

import javax.persistence.*;

@Entity(name = "IdentifyEmbeddedGrandChild")
@Table(name = "IDENTIFY_EMBEDDED_GRANDCHILD")
public class EmbeddedGrandChild {
    @EmbeddedId
    private EmbeddedGrandChildId grandChildId;

    @MapsId("childId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID"),
            @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    })
    private EmbeddedChild child;
    private String name;

    public EmbeddedGrandChildId getGrandChildId() {
        return grandChildId;
    }

    public void setGrandChildId(EmbeddedGrandChildId grandChildId) {
        this.grandChildId = grandChildId;
    }

    public EmbeddedChild getChild() {
        return child;
    }

    public void setChild(EmbeddedChild child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmbeddedGrandChild{" +
                "grandChildId=" + grandChildId +
                ", child=" + child +
                ", name='" + name + '\'' +
                '}';
    }
}
