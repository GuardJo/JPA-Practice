package com.practice.jpa.chapter07.entity.single;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SM")
public class SingleMovie extends SingleItem {
}
