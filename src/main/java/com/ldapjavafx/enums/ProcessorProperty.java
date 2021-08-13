package com.ldapjavafx.enums;

public enum ProcessorProperty implements PropertyEnum {

    LDAP_SERVER("processor.ldap.server"),
    LDAP_SECURITY_PRINCIPAL("processor.ldap.security.principal"),
    LDAP_SECURITY_PRINCIPAL_USER("processor.ldap.security.principal.user"),
    LDAP_PASSWORD("processor.ldap.password");

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
