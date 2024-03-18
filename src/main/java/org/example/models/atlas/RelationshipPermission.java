package org.example.models.atlas;

import java.util.List;

public class RelationshipPermission {
    List<String> privileges;
    List<String> relationshipTypes;
    List<String> end1EntityType;
    List<String> end1EntityId;
    List<String> end1EntityClassification;
    List<String> end2EntityType;
    List<String> end2EntityId;
    List<String> end2EntityClassification;

    public RelationshipPermission() {
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public List<String> getRelationshipTypes() {
        return relationshipTypes;
    }

    public void setRelationshipTypes(List<String> relationshipTypes) {
        this.relationshipTypes = relationshipTypes;
    }

    public List<String> getEnd1EntityType() {
        return end1EntityType;
    }

    public void setEnd1EntityType(List<String> end1EntityType) {
        this.end1EntityType = end1EntityType;
    }

    public List<String> getEnd1EntityId() {
        return end1EntityId;
    }

    public void setEnd1EntityId(List<String> end1EntityId) {
        this.end1EntityId = end1EntityId;
    }

    public List<String> getEnd1EntityClassification() {
        return end1EntityClassification;
    }

    public void setEnd1EntityClassification(List<String> end1EntityClassification) {
        this.end1EntityClassification = end1EntityClassification;
    }

    public List<String> getEnd2EntityType() {
        return end2EntityType;
    }

    public void setEnd2EntityType(List<String> end2EntityType) {
        this.end2EntityType = end2EntityType;
    }

    public List<String> getEnd2EntityId() {
        return end2EntityId;
    }

    public void setEnd2EntityId(List<String> end2EntityId) {
        this.end2EntityId = end2EntityId;
    }

    public List<String> getEnd2EntityClassification() {
        return end2EntityClassification;
    }

    public void setEnd2EntityClassification(List<String> end2EntityClassification) {
        this.end2EntityClassification = end2EntityClassification;
    }
}
