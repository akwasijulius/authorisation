package com.jogsoft.apps.assignment.auth.service;

import java.util.Set;

public interface AuthzCache {

    public Set<String> getRoles(String principal);

    public Set<Permission> getPermissions(String principal);

    public boolean isPermitted(String principal, Set<String> actions, Set<String> resources);


}
