package com.practice.jpa.chapter07.entity.identify.embeddedid;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "IdentifyEmbeddedParent")
@Table(name = "IDENTIFY_EMBEDDED_PARENT")
public class EmbeddedParent {
    @Id
    private String parentId;
    private String name;
}
