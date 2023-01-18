package com.practice.jpa.data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "NAME")
    private String userName;
    @Column(nullable = false)
    private int age;

    protected Member() {
    }

    protected Member(String id, String userName, int age) {
        this.id = id;
        this.userName = userName;
        this.age = age;
    }

    public static Member of(String id, String userName, int age) {
        return new Member(id, userName, age);
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return age == member.age && Objects.equals(id, member.id) && Objects.equals(userName, member.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, age);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
