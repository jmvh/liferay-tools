/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Jussi Hynninen
 */
@XmlType(name="finder")
public class Finder {
    
    @XmlAttribute(name="name")
    private String name;
    // Finders only need to support collection since primary keys already are searchable
    @XmlAttribute(name="return-type")
    private final String returnType = "Collection";
    @XmlElement(name="finder-column")
    private FinderColumn finderColumn;
    
    // <finder-column name="id" />
    public Finder() { }
    public Finder(Column c) {
        this.name = c.getFriendlyName();
        this.finderColumn = new FinderColumn(c.getName());
    }
    @XmlTransient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @XmlTransient
    public FinderColumn getFinderColumn() {
        return finderColumn;
    }

    public void setFinderColumn(FinderColumn finderColumn) {
        this.finderColumn = finderColumn;
    }

    @XmlType(name="finder-column")
    private static class FinderColumn {
       
        @XmlAttribute(name="name")
        private String name;
        
        public FinderColumn() { }
        public FinderColumn(String name) {
            this.name = name;
        }
        @XmlTransient
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
    }
}
