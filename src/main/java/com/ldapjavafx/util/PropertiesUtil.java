package com.ldapjavafx.util;


import com.ldapjavafx.enums.PropertyEnum;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUtil {


    private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();


    public static String getValue(PropertyEnum propertyEnum) {

        Properties properties = new Properties();
        if(!propertiesMap.containsKey(propertyEnum.getFileName())) {

            String fileName = propertyEnum.getFileName();
            try {
                properties.load(PropertiesUtil.class.getResourceAsStream(fileName));
            }catch (IOException e){
                System.out.println("ERROA O CARREGAR\n"+e.getMessage());
            }
        }else{
            properties = propertiesMap.get(propertyEnum.getFileName());
        }

        if (properties.containsKey(propertyEnum.getKey()))
            return (String) properties.get(propertyEnum.getKey());

        return null;
    }
}
