package com.jogsoft.apps.assignment.auth;

import com.jogsoft.apps.assignment.auth.model.AuthData;
import com.jogsoft.apps.assignment.auth.service.Permission;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.jogsoft.apps.assignment.auth.factory.PermissionFactory.createPermission;
import static com.jogsoft.apps.assignment.auth.type.PermissionType.*;

public class AuthAppProcessor {

    private static final Logger LOGGER = Logger.getLogger( AuthAppProcessor.class.getName() );

    /**
     * Process the properties file and load the authorisation data into a AuthData DTO
     *
     * @param propertiesFile the property file to read
     * @return AuthData the authorisation data
     * @throws IOException if an error occurs while reading the property file
     */
    public AuthData processFile(String propertiesFile) throws IOException{
        LOGGER.log(Level.INFO, "******* Processing Authorisation data: {0} *******", propertiesFile);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            if (inputStream == null) {
                throw new FileNotFoundException("property file '" + propertiesFile + "' not found in the classpath");
            }
            Properties properties = new Properties();
            properties.load(inputStream);

            // role to permission mapping
            Map<String, Set<Permission>> rolePermissionMappings = this.createRolePermissionMappings(properties);

            //user to role mapping
            Map<String, Set<String>> userRoleMappings = this.createUserRoleMappings(properties);

            LOGGER.log(Level.INFO, "******* Processing Authorisation data: {0} DONE *******", propertiesFile);

            return new AuthData(rolePermissionMappings, userRoleMappings);
        }
    }


    private Map<String, Set<Permission>> createRolePermissionMappings(Properties properties) {
        // Create the various permissions using data from the properties file
        Permission readOnlyPermission = createPermission(RO_PERM, properties.getProperty(RO_PERM.getType()));
        Permission readWritePermission = createPermission(RW_PERM, properties.getProperty(RW_PERM.getType()));
        Permission folderPermission = createPermission(FOLDER_PERM, properties.getProperty(FOLDER_PERM.getType()));

        Map<String, Set<Permission>> rolePermissionsMap = new HashMap<>();
        // Split the role->permission mappings line, and save them with the associated permission in the Role Permission Map
        // perms=RO->RO-Perm; RW->RW-Perm; SU->RW-Perm,Folder-Perm
        String[] perms = properties.getProperty("perms").split(";");
        for (String value : perms) {
            String[] permsValues = value.split("->");
            // RO->RO-Perm; RW->RW-Perm; SU->RW-Perm,Folder-Perm
            if(permsValues[0].equals("RO")) {
                rolePermissionsMap.put(permsValues[0], Collections.singleton(readOnlyPermission));
            }
            else if(permsValues[0].equals("RW")) {
                rolePermissionsMap.put(permsValues[0], Collections.singleton(readWritePermission));
            }
            else if(permsValues[0].equals("SU")) {
                //SU->RW-Perm,Folder-Perm
                Set<Permission> permissionSet = new HashSet<>();
                permissionSet.add(readWritePermission);
                permissionSet.add(folderPermission);
                rolePermissionsMap.put(permsValues[0], permissionSet);
            }
            else {
                LOGGER.log(Level.WARNING, "Unknown Role value encountered {0}", permsValues[0]);
            }
        }
        return rolePermissionsMap;
    }


    private Map<String, Set<String>> createUserRoleMappings(Properties properties) {
        // Split the user->role mappings line, and save them in the user role Map
        String[] users = properties.getProperty("users").split(";");
        //dopey->RO; sneezy->RW; happy->RW; grumpy->SU
        Map<String, Set<String>> usersRoleMap = new HashMap<>();
        for (String userRole : users) {
            String[] split = userRole.split("->");
            usersRoleMap.put(split[0], Collections.singleton(split[1]));
        }
        return usersRoleMap;
    }



}
