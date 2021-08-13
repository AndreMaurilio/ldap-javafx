package com.ldapjavafx.authentication;

import javax.naming.NamingException;

public interface AuthenticationLDAPService {

    String [] authenticateLDAP(String username, String passowrd) throws NamingException;

    String getMessage(String str);

}
