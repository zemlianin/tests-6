package org.example.models.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Dwh {
    @Id
    String name;

    @OneToMany()
    List<User> users;

    @OneToMany()
    List<Role> roles;

    boolean isUsed;

    public Dwh() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public boolean getUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
