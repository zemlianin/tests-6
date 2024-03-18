package org.example.models.atlas;

import java.util.ArrayList;
import java.util.List;

public class EntityPermission {
    List<String> privileges;
    List<String> entityTypes;
    List<String> entityIds;
    List<String> entityClassifications;
    List<String> labels;
    List<String> businessMetadata;
    List<String> attributes;
    List<String> classifications;

    public EntityPermission() {
        privileges = new ArrayList<>();
        entityTypes = new ArrayList<>();
        entityIds = new ArrayList<>();
        entityClassifications = new ArrayList<>();
        labels = new ArrayList<>();
        businessMetadata = new ArrayList<>();
        attributes = new ArrayList<>();
        classifications = new ArrayList<>();
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public List<String> getEntityTypes() {
        return entityTypes;
    }

    public void setEntityTypes(List<String> entityTypes) {
        this.entityTypes = entityTypes;
    }

    public List<String> getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(List<String> entityIds) {
        this.entityIds = entityIds;
    }

    public List<String> getEntityClassifications() {
        return entityClassifications;
    }

    public void setEntityClassifications(List<String> entityClassifications) {
        this.entityClassifications = entityClassifications;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getBusinessMetadata() {
        return businessMetadata;
    }

    public void setBusinessMetadata(List<String> businessMetadata) {
        this.businessMetadata = businessMetadata;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<String> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<String> classifications) {
        this.classifications = classifications;
    }
}
