/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml;

import java.io.File;
import java.util.Properties;

/**
 *
 * @author Jussi Hynninen
 */
public class ExportProperties extends Properties {
    private String dbName;
    private File dbProperties;
    
    public ExportProperties(File dbProperties) {
        this.dbProperties = dbProperties;
        //super(dbProperties);
    }
    
    public String getDBName() {
        return this.dbName;
    }
    
    public String getFriendlyName(String tableName) {
        String propertyKey =dbName+"."+tableName+"friendlyName";
        if(this.containsKey(propertyKey)) {
            return this.getProperty(propertyKey);
        } else {
            throw new IllegalArgumentException("Key "+propertyKey+" not found in "+dbProperties.getName());
        }
    }
}
