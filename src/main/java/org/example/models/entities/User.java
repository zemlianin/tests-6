package org.example.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.example.models.enums.PermissionLevel;

import java.util.UUID;

@Entity
public class User {
    @Id
    UUID id;

    @ManyToOne
    @JoinColumn(name = "dwh_name", nullable = true)
    Dwh dwh;

    @ManyToOne
    @JoinColumn(name = "role_name", nullable = true)
    Role role;

    public User() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Dwh getDwh() {
        return dwh;
    }

    public void setDwh(Dwh dwh) {
        this.dwh = dwh;
    }
}
