package com.ldapjavafx.authentication;

import com.ldapjavafx.enums.ProcessorProperty;
import com.ldapjavafx.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.Properties;
import javax.naming.*;
import javax.naming.directory.*;

public class AuthenticationLDAP implements AuthenticationLDAPService {


    @Override
    public String [] authenticateLDAP(String username, String password) throws NamingException {
        String[] atributes = new String [3];
        String u = getUsersCns(username);
        if(!u.equals("")) {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL,ProcessorProperty.LDAP_SERVER.getKey());
            props.put(Context.SECURITY_AUTHENTICATION, "simple");
            String dns = "CN="+u+","+ProcessorProperty.LDAP_SECURITY_PRINCIPAL.getKey();
            props.put(Context.SECURITY_PRINCIPAL,dns);
            props.put(Context.SECURITY_CREDENTIALS, password);

            try{
                InitialDirContext context = new InitialDirContext(props);

                SearchControls ctrls = new SearchControls();
                String seachFilter = "(displayName="+username+")";
                ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                ctrls.setReturningAttributes(new String[] {"description","displayName"});

//            String path = ("cn=" + username + ",ou="+u.getValue()+",ou=system,dc=pratice,dc=net");


                NamingEnumeration answers = context.search(dns, seachFilter, ctrls);

                SearchResult result = null;

                while (answers.hasMore()) {
                    result = (SearchResult) answers.next();
                    Attributes attributes = result.getAttributes();
                    Attribute attribute = attributes.get("description");
                    Attribute attribute2 = attributes.get("displayName");
                    atributes[0]=inCategory(attribute.get().toString());
                    atributes[1]=attribute2.get().toString();
                    if (!atributes[0].equals("ACESSO NEGADO")) {
                        context.close();
                        return atributes;
                    }

                }

            } catch (Exception e) {

            }
        }
        atributes[0] = "ACESSO NEGADO";
        return atributes;



    }





    public String inCategory(String category){
        if(category.equals("Vocalista")) {
            return "Hey ho let's go Joe!";
        }
        else if(category.equals("Baixista")) {
            return "Hey ho let's go DeeDee!";

        }
        else if(category.equals("Guitarrista")) {
            return "Hey ho let's go Johnny!";
        }
        else if(category.equals("Bateirista")) {
            return "Hey ho let's go Mark!";
        }
        return "ACESSO NEGADO,você não é da banda!";


    }

    @Override
    public String getMessage(String str){
        if(str.equals("dev")) return "Acesso como Desenvolvedor";
        if(str.equals("admin")) return "Acesso como Aministrador";
        return "";
    }

    public String getUsersCns(String username) throws NamingException {
        ArrayList<String> userNames = new ArrayList<>();
        String id = "";
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, ProcessorProperty.LDAP_SERVER.getKey());
        props.put(Context.SECURITY_AUTHENTICATION, "simple");
        props.put(Context.SECURITY_PRINCIPAL, ProcessorProperty.LDAP_SECURITY_PRINCIPAL_USER.getKey());
        props.put(Context.SECURITY_CREDENTIALS, ProcessorProperty.LDAP_PASSWORD.getKey());

        NamingEnumeration results = null;
        DirContext ctx = new InitialDirContext(props);


        SearchControls search = new SearchControls();
        search.setSearchScope(SearchControls.SUBTREE_SCOPE);
        results = ctx.search(ProcessorProperty.LDAP_SECURITY_PRINCIPAL.getKey(), "(objectClass=uidObject)", search);
//        results = ctx.search("OU=Dominio,DC=Dominio,DC=local", "(&(objectClass=user)(memberOf=" + dns + "))", search);


        while (results.hasMore()) {
            SearchResult searchResult = (SearchResult) results.next();
            Attributes attributes = searchResult.getAttributes();
            //Attribute attrCN = attributes.get("CN").toString(); //capturando o CommonName do usuário
//            Attribute attrSams = attributes.get("sAMAccountName");
            Attribute attrSams = attributes.get("displayName");
            if (attrSams.get().toString().equals(username)) id = attributes.get("cn").get().toString();
        }
        ctx.close();

        return id;

    }

}
