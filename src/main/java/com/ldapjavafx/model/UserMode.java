package com.ldapjavafx.model;

public enum UserMode {


    ADMIN("Administrador"),
    USER("Usuario");

    private String value;


    UserMode(String valu) {
    }

    public String getValue(){
        return value;
    }
}
