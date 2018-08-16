package com.jogsoft.apps.assignment.auth.type;

public enum RoleType {
    RO("Read Only"),
    RW("Read Write"),
    SU("Super User");


    private String name;

    RoleType(String name){
        this.name = name;
    };
}
