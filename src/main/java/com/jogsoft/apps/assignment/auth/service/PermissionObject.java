package com.jogsoft.apps.assignment.auth.service;

import java.util.Objects;
import java.util.Set;

public class PermissionObject implements Permission {

    private String name;
    private Set<String> actions;
    private Set<String> resources;

    public PermissionObject(String name, Set<String> actions, Set<String> resources) {
        this.name = name;
        this.actions = actions;
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getActions() {
        return actions;
    }

    @Override
    public Set<String> getResources() {
        return resources;
    }

    /**
     * returns true if actions is a subset of actions in this object AND
     * resources is a subset of resources in this object.
     *
     * @param actions set of actions
     * @param resources set of resources
     */
    @Override
    public boolean isPermitted(Set<String> actions, Set<String> resources) {
        return this.actions.containsAll(actions) && this.resources.containsAll(resources);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionObject that = (PermissionObject) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(actions, that.actions) &&
                Objects.equals(resources, that.resources);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, actions, resources);
    }
}
