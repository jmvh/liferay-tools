/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import fi.jmvh.liferay.db2servicexml.db.util.FalseExcludingBooleanAdapter;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Jussi Hynninen
 */
@XmlType(name="column")
public class Column {
   
    @XmlAttribute(name="name")
    private String friendlyName;
    @XmlAttribute(name="db-name")
    private String name;
    @XmlAttribute(name="primary")
    @XmlJavaTypeAdapter(FalseExcludingBooleanAdapter.class)
    private Boolean primary;
    private String type;
    @XmlAttribute(name="type")
    private String javaType;
    @XmlTransient
    private int length;

    public Column() {
    }
    
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
        
        javaType = type;
        for(SQLDatatypeEnum e : SQLDatatypeEnum.values()) {
            if(e.toString().equals(javaType)) {
                javaType = e.getJavaType();
            }
        }
    }

    @XmlTransient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlTransient
    public boolean isPrimaryKey() {
        return primary;
    }

    public void setPrimaryKey(boolean primary) {
        this.primary = primary;
    }

    @XmlTransient
    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getType() {
        return this.type;
 
    }
    
    @XmlTransient
    public int getLength() {
        return this.length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    @XmlTransient
    public String getJavaType() {
        return this.javaType;
    }
}
