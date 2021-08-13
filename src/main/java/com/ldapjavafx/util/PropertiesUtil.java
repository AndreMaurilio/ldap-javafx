package com.ldapjavafx.util;


import com.ldapjavafx.enums.PropertyEnum;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUtil {


    private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

    public static String getValue(PropertyEnum propertyEnum) {
        Properties properties = new Properties();

        if (properties.containsKey(propertyEnum.getKey()))
            return (String) properties.get(propertyEnum.getKey());

        return null;
    }
}
