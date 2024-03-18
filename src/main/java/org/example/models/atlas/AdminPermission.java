package org.example.models.atlas;

import java.util.ArrayList;
import java.util.List;

public class AdminPermission {
    List<String> privileges;

    public AdminPermission() {
        privileges = new ArrayList<>();
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void addPrivilege(String regex){
        privileges.add(regex);
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}
