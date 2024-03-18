package org.example.services;

import org.example.models.atlas.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AtlasRoleFactory {
    public AtlasRole createDefaultDwhRole(String fieldsWithAllPermission,
                                          String fieldsWithReadPermission) {
        var adminPermissions = createDefaultAdminPermissions();

        var entityPermissions = createDefaultEntityPermissions(
                fieldsWithAllPermission,
                fieldsWithReadPermission);

        var relationshipPermissions = createDefaultRelationshipPermissions(
                fieldsWithAllPermission,
                fieldsWithReadPermission);

        var typePermissions = createDefaultTypePermissions(
                fieldsWithAllPermission,
                fieldsWithReadPermission);

        return new AtlasRole(adminPermissions,
                typePermissions,
                entityPermissions,
                relationshipPermissions);
    }

    private List<AdminPermission> createDefaultAdminPermissions() {
        var list = new ArrayList<AdminPermission>();
        var permission = new AdminPermission();
        permission.addPrivilege(".*");
        list.add(permission);
        return list;
    }

    private List<EntityPermission> createDefaultEntityPermissions(String fieldsWithAllPermission,
                                                                  String fieldsWithReadPermission) {
        var firstEntityPermission = new EntityPermission();

        firstEntityPermission.setPrivileges(List.of(".*"));
        firstEntityPermission.setEntityTypes(List.of(fieldsWithAllPermission));
        firstEntityPermission.setEntityIds(List.of(".*"));
        firstEntityPermission.setClassifications(List.of(fieldsWithAllPermission));
        firstEntityPermission.setLabels(List.of(fieldsWithAllPermission));
        firstEntityPermission.setBusinessMetadata(List.of(".*"));
        firstEntityPermission.setAttributes(List.of(fieldsWithAllPermission));
        firstEntityPermission.setClassifications(List.of(fieldsWithAllPermission));

        var secondEntityPermission = new EntityPermission();

        secondEntityPermission.setPrivileges(List.of(".*read.*"));
        secondEntityPermission.setEntityTypes(List.of(fieldsWithReadPermission));
        secondEntityPermission.setEntityIds(List.of(".*"));
        secondEntityPermission.setClassifications(List.of(fieldsWithReadPermission));
        secondEntityPermission.setLabels(List.of(fieldsWithReadPermission));
        secondEntityPermission.setBusinessMetadata(List.of(".*"));
        secondEntityPermission.setAttributes(List.of(fieldsWithReadPermission));
        secondEntityPermission.setClassifications(List.of(fieldsWithReadPermission));

        return new ArrayList<>(List.of(firstEntityPermission, secondEntityPermission));
    }

    private List<RelationshipPermission> createDefaultRelationshipPermissions(String fieldsWithAllPermission,
                                                                              String fieldsWithReadPermission) {
        var firstRelationshipPermission = new RelationshipPermission();

        firstRelationshipPermission.setPrivileges(List.of(".*"));
        firstRelationshipPermission.setRelationshipTypes(List.of(fieldsWithAllPermission));
        firstRelationshipPermission.setEnd1EntityType(List.of(fieldsWithAllPermission));
        firstRelationshipPermission.setEnd1EntityId(List.of(".*"));
        firstRelationshipPermission.setEnd1EntityClassification(List.of(fieldsWithAllPermission));
        firstRelationshipPermission.setEnd2EntityType(List.of(fieldsWithAllPermission));
        firstRelationshipPermission.setEnd2EntityId(List.of(".*"));
        firstRelationshipPermission.setEnd2EntityClassification(List.of(fieldsWithAllPermission));

        var secondRelationshipPermission = new RelationshipPermission();

        secondRelationshipPermission.setPrivileges(List.of(".*read.*"));
        secondRelationshipPermission.setRelationshipTypes(List.of(fieldsWithReadPermission));
        secondRelationshipPermission.setEnd1EntityType(List.of(fieldsWithReadPermission));
        secondRelationshipPermission.setEnd1EntityId(List.of(".*"));
        secondRelationshipPermission.setEnd1EntityClassification(List.of(fieldsWithReadPermission));
        secondRelationshipPermission.setEnd2EntityType(List.of(fieldsWithReadPermission));
        secondRelationshipPermission.setEnd2EntityId(List.of(".*"));
        secondRelationshipPermission.setEnd2EntityClassification(List.of(fieldsWithReadPermission));

        return new ArrayList<>(List.of(firstRelationshipPermission, secondRelationshipPermission));
    }

    private List<TypePermission> createDefaultTypePermissions(String fieldsWithAllPermission,
                                                              String fieldsWithReadPermission) {
        var firstTypePermission = new TypePermission();
        firstTypePermission.setPrivileges(List.of(".*"));
        firstTypePermission.setTypeCategories(List.of(".*"));
        firstTypePermission.setTypeNames(List.of(fieldsWithAllPermission));

        var secondTypePermission = new TypePermission();
        secondTypePermission.setPrivileges(List.of(".*read.*"));
        secondTypePermission.setTypeCategories(List.of(".*"));
        secondTypePermission.setTypeNames(List.of(fieldsWithReadPermission));

        return new ArrayList<>(List.of(firstTypePermission, secondTypePermission));
    }
}
