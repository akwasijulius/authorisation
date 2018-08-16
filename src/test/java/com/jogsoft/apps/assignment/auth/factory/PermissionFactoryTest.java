package com.jogsoft.apps.assignment.auth.factory;

import com.jogsoft.apps.assignment.auth.service.Permission;
import com.jogsoft.apps.assignment.auth.type.PermissionType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class PermissionFactoryTest {



    @Test
    public void createRwPermissionShouldReturnValidRwPermission() {
        String data ="Read,Write,Delete:File";
        Permission permission = PermissionFactory.createPermission(PermissionType.RW_PERM, data);

        Assert.assertTrue(permission.getResources().size() == 1);
        Assert.assertTrue(permission.getResources().contains("File"));

        Assert.assertTrue(permission.getActions().size() == 3 );
        Set<String> actions = Stream.of("Write", "Delete", "Read").collect(Collectors.toCollection(HashSet::new));
        Assert.assertTrue(permission.getActions().containsAll(actions));
    }


    @Test
    public void createRoPermissionShouldReturnValidRoPermission() {
        String data ="Read:File";
        Permission permission = PermissionFactory.createPermission(PermissionType.RO_PERM, data);

        Assert.assertTrue(permission.getResources().size() == 1);
        Assert.assertTrue(permission.getResources().contains("File"));

        Assert.assertTrue(permission.getActions().size() == 1 );
        Set<String> actions = Stream.of("Read").collect(Collectors.toCollection(HashSet::new));
        Assert.assertTrue(permission.getActions().containsAll(actions));
    }

    @Test
    public void createFolderPermissionShouldReturnValidFolderPermission() {
        String data ="Create,Delete:Folder";
        Permission permission = PermissionFactory.createPermission(PermissionType.FOLDER_PERM, data);

        Assert.assertTrue(permission.getResources().size() == 1);
        Assert.assertTrue(permission.getResources().contains("Folder"));

        Assert.assertTrue(permission.getActions().size() == 2 );
        Set<String> actions = Stream.of("Create", "Delete").collect(Collectors.toCollection(HashSet::new));
        Assert.assertTrue(permission.getActions().containsAll(actions));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullTypePermissionShouldThrowException() {
        String data ="Create,Delete:Folder";
        PermissionFactory.createPermission(null, data);
    }
}