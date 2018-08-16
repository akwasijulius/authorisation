package com.jogsoft.apps.assignment.auth;

import com.jogsoft.apps.assignment.auth.model.AuthData;
import com.jogsoft.apps.assignment.auth.service.AuthzCache;
import com.jogsoft.apps.assignment.auth.service.AuthzCacheImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthzApp {
    private static final Logger LOGGER = Logger.getLogger( AuthzApp.class.getName() );
    private static String propertiesFile = "auth-data.properties";

    public static void main(String[] arg) throws Exception {
        // Create the Processor, read the authorisation properties file and return the Authorisation data DTO
        AuthAppProcessor processor = new AuthAppProcessor();
        AuthData authData = processor.processFile(propertiesFile);

        // Create the AuthzCache and initialise it with the authorisation data
        AuthzCache authzCache = new AuthzCacheImpl(authData);

        //Now that data is loaded, we perform a series of checks

        //1: Check if happy with RW permissions is permitted to "Write", "Delete", "Read" a File - this should be permitted
        Set<String> actions = Stream.of("Write", "Delete", "Read").collect(Collectors.toCollection(HashSet::new));
        Set<String> resources = Stream.of("File").collect(Collectors.toCollection(HashSet::new));

        LOGGER.log(Level.INFO, "Checking {0} permission on resource {1} for {2}", new Object[]{ actions, resources, "happy" });

        boolean isPermitted = authzCache.isPermitted("happy", actions, resources);

        String message = isPermitted ? "PERMITTED" : "DENIED";
        LOGGER.log(Level.INFO, message);


        //2: Check if sneezy with RW permission is permitted to Create and Delete a Folder - this should be denied
        actions = Stream.of("Create", "Delete").collect(Collectors.toCollection(HashSet::new));
        resources = Stream.of("Folder").collect(Collectors.toCollection(HashSet::new));

        LOGGER.log(Level.INFO, "Checking {0} permission on resource {1} for {2}", new Object[]{ actions, resources, "sneezy" });

        isPermitted = authzCache.isPermitted("sneezy", actions, resources);

        message = isPermitted ? "PERMITTED" : "DENIED";
        LOGGER.log(Level.INFO, message);

    }
}
