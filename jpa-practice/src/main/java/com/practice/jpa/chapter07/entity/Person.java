package com.practice.jpa.chapter07.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PERSON_ID")),
        @AttributeOverride(name = "name", column = @Column(name = "PERSON_NAME"))
})
public class Person extends BaseEntity {
    private String email;

    protected Person() {

    }

    private Person(Long id, String name, String email) {
        super(id, name);
        this.email = email;
    }

    public static Person of(Long id, String name, String email) {
        return new Person(id, name, email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }
}
