package com.jogsoft.apps.assignment.auth.service;


import com.jogsoft.apps.assignment.auth.model.AuthData;

import java.util.*;

public class AuthzCacheImpl implements AuthzCache {

    private Map<String, Set<String>> userRoles;
    private Map<String, Set<Permission>> rolePermissions;


    /**
     * Create the AuthzCacheImpl and initialises the AuthzCache with the authorisation data
     *
     * @param authData the per populated authorisation data, which should not be null
     * @throws IllegalAccessException if the authData is null
     */
    public AuthzCacheImpl(AuthData authData) throws IllegalAccessException {
        if(authData == null){
            throw new IllegalAccessException("Authorisation data should not be null");
        }
        this.userRoles = authData.getUserRoleMappings();
        this.rolePermissions = authData.getRolePermissionMappings();
    }

    public Set<String> getRoles(String principal) {
        return userRoles.get(principal);
    }

    public Set<Permission> getPermissions(String principal) {
        Set<String> roles = this.getRoles(principal);
        Set<Permission> permissions = new HashSet<>();
        for (String role: roles) {
            permissions.addAll(rolePermissions.get(role));
        }
        return permissions;
    }

    public boolean isPermitted(String principal, Set<String> actions, Set<String> resources) {
        return getPermissions(principal).stream().anyMatch(permission -> permission.isPermitted(actions, resources));
    }


}
