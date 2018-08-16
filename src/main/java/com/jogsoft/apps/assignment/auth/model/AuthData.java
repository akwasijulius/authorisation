package com.jogsoft.apps.assignment.auth.model;

import com.jogsoft.apps.assignment.auth.service.Permission;

import java.util.Map;
import java.util.Set;

public class AuthData {
    private Map<String, Set<Permission>> rolePermissionMappings;
    private Map<String, Set<String>> userRoleMappings;


    public AuthData(Map<String, Set<Permission>> rolePermissionMappings, Map<String, Set<String>> userRoleMappings) {
        this.rolePermissionMappings = rolePermissionMappings;
        this.userRoleMappings = userRoleMappings;
    }


    public Map<String, Set<Permission>> getRolePermissionMappings() {
        return rolePermissionMappings;
    }

    public Map<String, Set<String>> getUserRoleMappings() {
        return userRoleMappings;
    }

}
