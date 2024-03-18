package org.example.models.atlas;

import java.util.List;

public class TypePermission {
    List<String> privileges;
    List<String> typeCategories;
    List<String> typeNames;

    public TypePermission() {
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public List<String> getTypeCategories() {
        return typeCategories;
    }

    public void setTypeCategories(List<String> typeCategories) {
        this.typeCategories = typeCategories;
    }

    public List<String> getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(List<String> typeNames) {
        this.typeNames = typeNames;
    }
}
