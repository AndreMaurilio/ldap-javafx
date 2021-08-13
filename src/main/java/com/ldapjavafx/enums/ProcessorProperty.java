package com.ldapjavafx.enums;

public enum ProcessorProperty implements PropertyEnum {

    LDAP_SERVER("ldap://127.0.0.1:389"),
    LDAP_SECURITY_PRINCIPAL("ou=pessoas,dc=javaldap,dc=com"),
    LDAP_SECURITY_PRINCIPAL_USER("cn=admin,dc=javaldap,dc=com"),
    LDAP_PASSWORD("root");

    private String key;

    private String file = "ldapjava.properties";

    ProcessorProperty(String key){
        this.key = key;

    }

    @Override
    public String getFileName() {
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
