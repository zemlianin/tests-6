package org.example.models.atlas;

import java.util.List;

public class AtlasRole {
    List<AdminPermission> adminPermissions;
    List<TypePermission> typePermissions;
    List<EntityPermission> entityPermissions;
    List<RelationshipPermission> relationshipPermissions;

    public AtlasRole() {
    }

    public AtlasRole(List<AdminPermission> adminPermissions,
                     List<TypePermission> typePermissions,
                     List<EntityPermission> entityPermissions,
                     List<RelationshipPermission> relationshipPermissions) {
        this.adminPermissions = adminPermissions;
        this.typePermissions = typePermissions;
        this.entityPermissions = entityPermissions;
        this.relationshipPermissions = relationshipPermissions;
    }

    public List<AdminPermission> getAdminPermissions() {
        return adminPermissions;
    }

    public void setAdminPermissions(List<AdminPermission> adminPermissions) {
        this.adminPermissions = adminPermissions;
    }

    public List<TypePermission> getTypePermissions() {
        return typePermissions;
    }

    public void setTypePermissions(List<TypePermission> typePermissions) {
        this.typePermissions = typePermissions;
    }

    public List<EntityPermission> getEntityPermissions() {
        return entityPermissions;
    }

    public void setEntityPermissions(List<EntityPermission> entityPermissions) {
        this.entityPermissions = entityPermissions;
    }

    public List<RelationshipPermission> getRelationshipPermissions() {
        return relationshipPermissions;
    }

    public void setRelationshipPermissions(List<RelationshipPermission> relationshipPermissions) {
        this.relationshipPermissions = relationshipPermissions;
    }
}
