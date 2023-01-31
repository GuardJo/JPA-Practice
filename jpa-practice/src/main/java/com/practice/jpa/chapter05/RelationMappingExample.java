package com.practice.jpa.chapter05;

import com.practice.jpa.chapter05.data.Person;
import com.practice.jpa.chapter05.data.Team;

public class RelationMappingExample implements Runnable{
    @Override
    public void run() {
        Person person = new Person();
        Team team = new Team();
        team.setName("testTeam");

        person.setName("test");
        person.setTeam(team);

        System.out.println(person.getName() + " " + person.getTeam().getName());
    }
}
