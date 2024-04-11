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
                ".*",
                ".*read.*",
                fieldsWithAllPermission,
                fieldsWithReadPermission);

        var relationshipPermissions = createDefaultRelationshipPermissions(
                ".*",
                ".*read.*",
                fieldsWithAllPermission,
                fieldsWithReadPermission);

        var typePermissions = createDefaultTypePermissions(
                ".*",
                ".*read.*",
                fieldsWithAllPermission,
                fieldsWithReadPermission);

        return new AtlasRole(adminPermissions,
                typePermissions,
                entityPermissions,
                relationshipPermissions);
    }

    public AtlasRole createOnlyReadDwhRole(String fieldsWithAllPermission,
                                          String fieldsWithReadPermission) {
        var adminPermissions = createDefaultAdminPermissions();

        var entityPermissions = createDefaultEntityPermissions(
                ".*read.*",
                ".*read.*",
                fieldsWithAllPermission,
                fieldsWithReadPermission);

        var relationshipPermissions = createDefaultRelationshipPermissions(
                ".*read.*",
                ".*read.*",
                fieldsWithAllPermission,
                fieldsWithReadPermission);

        var typePermissions = createDefaultTypePermissions(
                ".*read.*",
                ".*read.*",
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

    private List<EntityPermission> createDefaultEntityPermissions(String firstPermissions,
                                                                  String secondPermissions,
                                                                  String fieldsWithAllPermission,
                                                                  String fieldsWithReadPermission) {
        var firstEntityPermission = new EntityPermission();

        firstEntityPermission.setPrivileges(List.of(firstPermissions));
        firstEntityPermission.setEntityTypes(List.of(fieldsWithAllPermission));
        firstEntityPermission.setEntityIds(List.of(".*"));
        firstEntityPermission.setClassifications(List.of(fieldsWithAllPermission));
        firstEntityPermission.setLabels(List.of(fieldsWithAllPermission));
        firstEntityPermission.setBusinessMetadata(List.of(".*"));
        firstEntityPermission.setAttributes(List.of(fieldsWithAllPermission));
        firstEntityPermission.setClassifications(List.of(fieldsWithAllPermission));

        var secondEntityPermission = new EntityPermission();

        secondEntityPermission.setPrivileges(List.of(secondPermissions));
        secondEntityPermission.setEntityTypes(List.of(fieldsWithReadPermission));
        secondEntityPermission.setEntityIds(List.of(".*"));
        secondEntityPermission.setClassifications(List.of(fieldsWithReadPermission));
        secondEntityPermission.setLabels(List.of(fieldsWithReadPermission));
        secondEntityPermission.setBusinessMetadata(List.of(".*"));
        secondEntityPermission.setAttributes(List.of(fieldsWithReadPermission));
        secondEntityPermission.setClassifications(List.of(fieldsWithReadPermission));

        return new ArrayList<>(List.of(firstEntityPermission, secondEntityPermission));
    }

    private List<RelationshipPermission> createDefaultRelationshipPermissions(String firstPermissions,
                                                                              String secondPermissions,
                                                                              String fieldsWithAllPermission,
                                                                              String fieldsWithReadPermission) {
        var firstRelationshipPermission = new RelationshipPermission();

        firstRelationshipPermission.setPrivileges(List.of(firstPermissions));
        firstRelationshipPermission.setRelationshipTypes(List.of(fieldsWithAllPermission));
        firstRelationshipPermission.setEnd1EntityType(List.of(fieldsWithAllPermission));
        firstRelationshipPermission.setEnd1EntityId(List.of(".*"));
        firstRelationshipPermission.setEnd1EntityClassification(List.of(fieldsWithAllPermission));
        firstRelationshipPermission.setEnd2EntityType(List.of(fieldsWithAllPermission));
        firstRelationshipPermission.setEnd2EntityId(List.of(".*"));
        firstRelationshipPermission.setEnd2EntityClassification(List.of(fieldsWithAllPermission));

        var secondRelationshipPermission = new RelationshipPermission();

        secondRelationshipPermission.setPrivileges(List.of(secondPermissions));
        secondRelationshipPermission.setRelationshipTypes(List.of(fieldsWithReadPermission));
        secondRelationshipPermission.setEnd1EntityType(List.of(fieldsWithReadPermission));
        secondRelationshipPermission.setEnd1EntityId(List.of(".*"));
        secondRelationshipPermission.setEnd1EntityClassification(List.of(fieldsWithReadPermission));
        secondRelationshipPermission.setEnd2EntityType(List.of(fieldsWithReadPermission));
        secondRelationshipPermission.setEnd2EntityId(List.of(".*"));
        secondRelationshipPermission.setEnd2EntityClassification(List.of(fieldsWithReadPermission));

        return new ArrayList<>(List.of(firstRelationshipPermission, secondRelationshipPermission));
    }

    private List<TypePermission> createDefaultTypePermissions(String firstPermissions,
                                                              String secondPermissions,
                                                              String fieldsWithAllPermission,
                                                              String fieldsWithReadPermission) {
        var firstTypePermission = new TypePermission();
        firstTypePermission.setPrivileges(List.of(firstPermissions));
        firstTypePermission.setTypeCategories(List.of(".*"));
        firstTypePermission.setTypeNames(List.of(fieldsWithAllPermission));

        var secondTypePermission = new TypePermission();
        secondTypePermission.setPrivileges(List.of(secondPermissions));
        secondTypePermission.setTypeCategories(List.of(".*"));
        secondTypePermission.setTypeNames(List.of(fieldsWithReadPermission));

        return new ArrayList<>(List.of(firstTypePermission, secondTypePermission));
    }
}
