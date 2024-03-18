package org.example.models.dao;

import org.example.models.entities.Dwh;

public class DwhResponse {
    String name;

    public DwhResponse() {
    }
    public DwhResponse(Dwh dwh) {
        name = dwh.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
