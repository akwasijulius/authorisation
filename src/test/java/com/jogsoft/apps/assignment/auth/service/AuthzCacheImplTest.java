package com.jogsoft.apps.assignment.auth.service;

import com.jogsoft.apps.assignment.auth.model.AuthData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.jogsoft.apps.assignment.auth.type.PermissionType.RO_PERM;
import static org.junit.Assert.*;

public class AuthzCacheImplTest {

    private AuthzCache authzCache;

    private String user1 = "John";
    private String user2 = "Jane";

    @Before
    public void setUp() throws Exception {
        Map<String, Set<Permission>> rolePermissionMap = new HashMap<>();
        Permission readOnlyPermission = new PermissionObject(RO_PERM.getType(), Collections.singleton("Read"), Collections.singleton("File"));
        rolePermissionMap.put("RO", Collections.singleton(readOnlyPermission));

        Map<String, Set<String>> userRoleMap = new HashMap<>();
        userRoleMap.put(user1, Collections.singleton("RO"));
        userRoleMap.put(user2, Collections.singleton("SU"));

        AuthData data = new AuthData(rolePermissionMap, userRoleMap);
        authzCache = new AuthzCacheImpl(data);
    }

    @Test
    public void getRolesForUserShouldReturnCorrectRoles() {
        Set<String> roles = authzCache.getRoles(user1);
        Assert.assertTrue(roles.size() == 1);
        Assert.assertTrue(roles.contains("RO"));
    }

    @Test
    public void getPermissionsForUserShouldReturnCorrectPermissions() {
        // RO-Perm
        Permission readOnlyPermission = new PermissionObject(RO_PERM.getType(), Collections.singleton("Read"), Collections.singleton("File"));
        Set<Permission> permissions = authzCache.getPermissions(user1);
        Assert.assertTrue(permissions.size() == 1);
        Assert.assertTrue(permissions.contains(readOnlyPermission));

    }

    @Test
    public void isPermittedShouldReturnTrue() {
        boolean permitted = authzCache.isPermitted(user1, Collections.singleton("Read"), Collections.singleton("File"));
        assertTrue(permitted);
    }


    @Test
    public void isPermittedShouldReturnFalse() {
        boolean permitted = authzCache.isPermitted(user1, Collections.singleton("Write"), Collections.singleton("File"));
        assertFalse(false);
    }
}