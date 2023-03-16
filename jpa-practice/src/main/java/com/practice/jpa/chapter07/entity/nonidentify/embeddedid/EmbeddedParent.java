package com.practice.jpa.chapter07.entity.nonidentify.embeddedid;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class EmbeddedParent {
    @EmbeddedId
    private EmbeddedParentId parentId;

    private String name;

    @Override
    public String toString() {
        return "EmbeddedParent{" +
                "parentId=" + parentId +
                ", name='" + name + '\'' +
                '}';
    }
}
