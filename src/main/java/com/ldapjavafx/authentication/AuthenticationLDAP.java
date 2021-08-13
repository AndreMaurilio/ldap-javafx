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
            props.put(Context.PROVIDER_URL, PropertiesUtil.getValue(ProcessorProperty.LDAP_SERVER));
            props.put(Context.SECURITY_AUTHENTICATION, "simple");
            String dns = "CN="+u+","+PropertiesUtil.getValue(ProcessorProperty.LDAP_SECURITY_PRINCIPAL);
            props.put(Context.SECURITY_PRINCIPAL,dns);
            props.put(Context.SECURITY_CREDENTIALS, password);

            try{
                InitialDirContext context = new InitialDirContext(props);

                SearchControls ctrls = new SearchControls();
                String seachFilter = "(sAMAccountName="+username+")";
                ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                ctrls.setReturningAttributes(new String[] {"deion","displayName"});

//            String path = ("cn=" + username + ",ou="+u.getValue()+",ou=system,dc=pratice,dc=net");


                NamingEnumeration answers = context.search(dns, seachFilter, ctrls);

                SearchResult result = null;

                while (answers.hasMore()) {
                    result = (SearchResult) answers.next();
                    Attributes attributes = result.getAttributes();
                    Attribute attribute = attributes.get("deion");
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
        if(category.equals("Setor - Desenvolvimento")) {
            return "dev";
        }
        else if(category.equals("Setor - Processamento")) {
            return "admin";

        }
        else if(category.equals("Setor - SAC")) {
            return "admin";
        }
        else if(category.equals("Setor - Gestão de Contratos")) {
            return "admin";
        }
        return "ACESSO NEGADO";


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
        props.put(Context.PROVIDER_URL, PropertiesUtil.getValue(ProcessorProperty.LDAP_SERVER));
        props.put(Context.SECURITY_AUTHENTICATION, "simple");
        String dns = "OU=Usuarios,OU=Dominio,DC=Dominio,DC=local";
        String dn = "CN=integration,OU=Usuarios,OU=Dominio,DC=Dominio,DC=local";
        props.put(Context.SECURITY_PRINCIPAL, PropertiesUtil.getValue(ProcessorProperty.LDAP_SECURITY_PRINCIPAL_USER));
        props.put(Context.SECURITY_CREDENTIALS, PropertiesUtil.getValue(ProcessorProperty.LDAP_PASSWORD));

        NamingEnumeration results = null;
        DirContext ctx = new InitialDirContext(props);


        SearchControls search = new SearchControls();
        search.setSearchScope(SearchControls.SUBTREE_SCOPE);
        results = ctx.search(PropertiesUtil.getValue(ProcessorProperty.LDAP_SECURITY_PRINCIPAL), "(objectClass=user)", search);
//        results = ctx.search("OU=Dominio,DC=Dominio,DC=local", "(&(objectClass=user)(memberOf=" + dns + "))", search);


        while (results.hasMore()) {
            SearchResult searchResult = (SearchResult) results.next();
            Attributes attributes = searchResult.getAttributes();
            //Attribute attrCN = attributes.get("CN").toString(); //capturando o CommonName do usuário
            Attribute attrSams = attributes.get("sAMAccountName");
            if (attrSams.get().toString().equals(username)) id = attributes.get("CN").get().toString();
        }
        ctx.close();

        return id;

    }

}
