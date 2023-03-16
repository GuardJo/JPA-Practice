package com.practice.jpa.chapter07.entity.single;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SA")
public class SingleAlbum extends SingleItem{
}
