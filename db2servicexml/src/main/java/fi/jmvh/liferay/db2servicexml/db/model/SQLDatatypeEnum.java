/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

/**
 *
 * @author Jussi Hynninen
 */
public enum SQLDatatypeEnum {
    varchar("String"),
    numeric("long");
    
    private String javaType;
    
    SQLDatatypeEnum(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaType() {
        return javaType;
    }
    
}
