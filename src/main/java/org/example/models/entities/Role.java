package org.example.models.entities;

import jakarta.persistence.*;
import org.example.models.enums.PermissionLevel;

import java.util.List;

@Entity
public class Role {
    @Id
    String name;

    @OneToMany()
    List<User> users;

    @ManyToOne
    @JoinColumn(name = "dwh_name", nullable = true)
    Dwh dwh;

    @Enumerated(EnumType.ORDINAL)
    PermissionLevel permissionLevel;

    public Role() {
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
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

    public Dwh getDwh() {
        return dwh;
    }

    public void setDwh(Dwh dwh) {
        this.dwh = dwh;
    }
}
