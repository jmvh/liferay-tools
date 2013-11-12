/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Jussi Hynninen
 */
public class DataTypes {
    private static final HashMap<String,String> types;
    private static final List<String> excludedFromHints;
    static {
        types = new HashMap<String,String>();
        types.put("varchar", "String");
        types.put("numeric", "long");
        types.put("boolean", "Boolean");
        types.put("bool", "Boolean");
        
        excludedFromHints = new ArrayList<String>();
        excludedFromHints.add("boolean");
        excludedFromHints.add("bool");
    }
            

    /**
     * @param sqlType
     * @return Java type for given SQL type
     */
    public static String getJavaType(String sqlType) {
        if(types.containsKey(sqlType)) {
            return types.get(sqlType);
        }
        return sqlType;
    }
    
    /**
     * @param javaType
     * @return SQL type for given Java type
     */
    public static String getSqlType(String javaType) {
        if(types.containsValue(javaType)) {
            for(Entry<String,String> e : types.entrySet()) {
                if(e.getValue().equals(javaType)) {
                    return e.getKey();
                }
            }
        }
        return javaType;
    }
    
    /**
     * @param sqlType
     * @return true if the given SQL type should be excluded from portlet-model-hints.xml
     */
    public static boolean excludeFromHints(String sqlType) {
        return excludedFromHints.contains(sqlType);
    }

}
