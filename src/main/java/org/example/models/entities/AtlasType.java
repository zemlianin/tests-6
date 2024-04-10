package org.example.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.example.models.atlas.EntityType;

@Entity
public class AtlasType {
    @Id
    String name;
    String createdBy;
    String updatedBy;
    String createTime;
    String updateTime;
    String version;

    public AtlasType() {
    }
    public AtlasType(EntityType entityType) {
        createdBy = entityType.getCreatedBy();
        updatedBy = entityType.getUpdatedBy();
        createTime = entityType.getCreateTime();
        updateTime = entityType.getUpdateTime();
        version = entityType.getVersion();
        name = entityType.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
