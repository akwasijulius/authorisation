package com.jogsoft.apps.assignment.auth.type;

public enum PermissionType {

    RO_PERM("RO-Perm"),
    RW_PERM("RW-Perm"),
    FOLDER_PERM("Folder-Perm");

    private String type;

    PermissionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
