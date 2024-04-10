package org.example.models.atlas;

import java.util.ArrayList;

public class AtlasTypeResponse {
    public AtlasTypeResponse() {
    }

    ArrayList<EntityType> enumDefs;
    ArrayList<EntityType> structDefs;
    ArrayList<EntityType> entityDefs;

    public ArrayList<EntityType> getEnumDefs() {
        return enumDefs;
    }

    public void setEnumDefs(ArrayList<EntityType> enumDefs) {
        this.enumDefs = enumDefs;
    }

    public ArrayList<EntityType> getStructDefs() {
        return structDefs;
    }

    public void setStructDefs(ArrayList<EntityType> structDefs) {
        this.structDefs = structDefs;
    }

    public ArrayList<EntityType> getEntityDefs() {
        return entityDefs;
    }

    public void setEntityDefs(ArrayList<EntityType> entityDefs) {
        this.entityDefs = entityDefs;
    }
}
