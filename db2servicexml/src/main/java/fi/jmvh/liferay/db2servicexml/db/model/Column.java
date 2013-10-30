/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

/**
 *
 * @author Jussi Hynninen
 */
public class Column {
    
    private String name;
    private String friendlyName;
    private boolean primary;
    private String type;

    public Column(String name, String type) {
        init(name,type,false);
    }
    
    public Column(String name, String type, boolean primary) {
        init(name,type,primary);
    }
    
    private void init(String name, String type, boolean primary) {
        this.name = name;
        friendlyName = name;
        this.type = type;
        this.primary = primary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimaryKey() {
        return primary;
    }

    public void setPrimaryKey(boolean primary) {
        this.primary = primary;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getType() {
        return this.type;
    }
    
    public String toServiceXML() {
        String javaType = getType();
        for(SQLDatatypeEnum e : SQLDatatypeEnum.values()) {
            if(e.toString().equals(javaType)) {
                javaType = e.getJavaType();
            }
        }
        String ret = "";
        ret += "\t<column name=\""+getFriendlyName()+"\" db-name=\""+getName()+"\" type=\""+javaType+"\"";
        if(isPrimaryKey()) {
            ret += " primary=\"true\"";
        }
        ret += " />\n";
        return ret;
    }
    
}
