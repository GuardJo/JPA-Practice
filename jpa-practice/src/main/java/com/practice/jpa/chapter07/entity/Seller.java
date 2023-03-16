package com.practice.jpa.chapter07.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "SELLER_ID"))
public class Seller extends BaseEntity {
    private String shopName;

    protected Seller() {

    }

    private Seller(Long id, String name, String shopName) {
        super(id, name);
        this.shopName = shopName;
    }

    public static Seller of(Long id, String name, String shopName) {
        return new Seller(id, name, shopName);
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Seller seller = (Seller) o;
        return Objects.equals(shopName, seller.shopName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), shopName);
    }
}
