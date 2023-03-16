package com.practice.jpa.chapter07.entity.single;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SB")
public class SingleBook extends SingleItem{
}
