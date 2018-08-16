package com.jogsoft.apps.assignment.auth.factory;

import com.jogsoft.apps.assignment.auth.service.Permission;
import com.jogsoft.apps.assignment.auth.service.PermissionObject;
import com.jogsoft.apps.assignment.auth.type.PermissionType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.jogsoft.apps.assignment.auth.type.PermissionType.*;
import static com.jogsoft.apps.assignment.auth.type.PermissionType.RW_PERM;

public class PermissionFactory {

    /**
     * Creates Permission Object based on the type and the data provided
     *
     * @param permissionType permission type
     * @param data the delimited data containing actions and resources i.e (Read,Write,Delete:File)
     * @return permission
     */
    public static Permission createPermission(PermissionType permissionType, String data)  {
        if (permissionType == RO_PERM){
            String[] splits = data.split(":");
            String[] actionValues = (splits[0].split(","));
            Set<String> actions = new HashSet<>(Arrays.asList(actionValues));
            return new PermissionObject(RO_PERM.getType(), actions, Collections.singleton(splits[1]));
        }
        else if(permissionType == RW_PERM){
            String[] splits = data.split(":");
            String[] actionValues = (splits[0].split(","));
            Set<String> actions = new HashSet<>(Arrays.asList(actionValues));
            return new PermissionObject(RW_PERM.getType(), actions, Collections.singleton(splits[1]));
        }
        else if(permissionType == FOLDER_PERM){
            String[] splits = data.split(":");
            String[] actionValues = (splits[0].split(","));
            Set<String> actions = new HashSet<>(Arrays.asList(actionValues));
            return new PermissionObject(FOLDER_PERM.name(), actions, Collections.singleton(splits[1]));
        }
        else{
            throw new IllegalArgumentException("Invalid permission type" + permissionType);
        }
    }
}
