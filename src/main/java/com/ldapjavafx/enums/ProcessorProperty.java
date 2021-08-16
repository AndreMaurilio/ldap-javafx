package com.ldapjavafx.enums;

public enum ProcessorProperty implements PropertyEnum {

    LDAP_SERVER("processor.ldap.server"),
    LDAP_SECURITY_PRINCIPAL("processor.ldap.security.principal"),
    LDAP_SECURITY_PRINCIPAL_USER("processor.ldap.security.principal.user"),
    LDAP_PASSWORD("processor.ldap.password"),

    IMAGE_PATH("/img/");
    private String key;

    private String file = "/properties/ldapjavafx.properties";
    private String image = "/img/";

    ProcessorProperty(String key){
        this.key = key;

    }

    @Override
    public String getFileName() {
        return file;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
