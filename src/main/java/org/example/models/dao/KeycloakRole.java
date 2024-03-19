package org.example.models.dao;

import java.util.UUID;

public class KeycloakRole {
    UUID id;
    String name;
    String description;
    Boolean composite;
    Boolean clientRole;
    String containerId;

    public KeycloakRole() {
    }

    public KeycloakRole(UUID id,
                        String name,
                        String description,
                        Boolean composite,
                        Boolean clientRole,
                        String containerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.composite = composite;
        this.clientRole = clientRole;
        this.containerId = containerId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getComposite() {
        return composite;
    }

    public void setComposite(Boolean composite) {
        this.composite = composite;
    }

    public Boolean getClientRole() {
        return clientRole;
    }

    public void setClientRole(Boolean clientRole) {
        this.clientRole = clientRole;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
}
