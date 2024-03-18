package org.example.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class User {
    @Id
    UUID id;

    @ManyToOne
    @JoinColumn(name = "dwh_name")
    Dwh dwh;

    public User() {
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
