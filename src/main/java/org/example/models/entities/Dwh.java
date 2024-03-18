package org.example.models.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Dwh {
    @Id
    String name;

    @OneToMany()
    List<Dwh> users;

    public Dwh() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dwh> getUsers() {
        return users;
    }

    public void setUsers(List<Dwh> users) {
        this.users = users;
    }
}
