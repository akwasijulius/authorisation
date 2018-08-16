package com.jogsoft.apps.assignment.auth.service;

import java.util.Set;

public interface Permission {

    /**
     * Returns a set of permitted actions
     * @return Set<String>
     */
    public Set<String> getActions();

    /**
     * Returns a set of permitted resources
     * @return Set<String>
     */
    public Set<String> getResources();


    /**
     * returns true if actions is a subset of actions in this object AND
     * resources is a subset of resources in this object.
     **/
    public boolean isPermitted(Set<String> actions, Set<String> resources);
}
