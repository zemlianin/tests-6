package org.example.models.dao;

public class RoleRequest {
    String name;
    String description;
    Boolean composite;

    public RoleRequest() {
    }

    public RoleRequest(String name, String description, Boolean composite) {
        this.name = name;
        this.description = description;
        this.composite = composite;
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
}
