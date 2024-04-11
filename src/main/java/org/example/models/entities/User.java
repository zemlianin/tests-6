package org.example.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.example.models.enums.DwhRole;

import java.util.UUID;

@Entity
public class User {
    @Id
    UUID id;

    @ManyToOne
    @JoinColumn(name = "dwh_name", nullable = true)
    Dwh dwh;

    @Enumerated(EnumType.ORDINAL)
    private DwhRole dwhRole;

    public User() {
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
