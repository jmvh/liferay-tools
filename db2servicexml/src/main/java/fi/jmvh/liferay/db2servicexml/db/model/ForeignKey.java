/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

/**
 *
 * @author Jussi Hynninen
 */
public class ForeignKey {
  
    
    private String name;
    private String friendlyName;
    // Foreign key table name (PKTABLE_NAME)
    private String fkTable;
    // Foreign key column name (PKCOLUMN_NAME)
    private String fkColumn;
    
    /**
     * 
     * @param name Name of the foreign key
     * @param fkTable Friendly name of the referenced table
     * @param fkColumn Friendly name of the referenced column
     */
    public ForeignKey(String name, String fkTable, String fkColumn) {
        this.name = name;
        friendlyName = name;
        this.fkTable = fkTable;
        this.fkColumn = fkColumn;
    }
    
    public String toServiceXML() {
        return "\t<column name=\""+this.getName()+"\" type=\""+fkTable+"\" entity=\""+fkTable+"\" mapping-key=\""+fkColumn+"\" />\n";
    }

    public String getName() {
        return name;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getFkTable() {
        return fkTable;
    }

    public String getFkColumn() {
        return fkColumn;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
    
}
