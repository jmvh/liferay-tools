/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import fi.jmvh.liferay.db2servicexml.db.util.DBImporter;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jussi Hynninen
 */
@XmlRootElement(name="service-builder")
public class Database {
    
    @XmlElement(name="author")
    private String author;
    @XmlElement(name="namespace")
    private String dbName;
    private HashMap<String,Table> tables;
    @XmlAttribute(name="package-path")
    private String packagePath;
    @XmlAttribute(name="auto-namespace-tables")
    private boolean autoNamespaceTables;
    
    public Database() {
        
        init(null);
    }
    
    public Database(String dbName) {
        init(dbName);
    }
    
    private void init(String dbName) {
        this.author = "Your Name";
        this.dbName = "MyDatabase";
        if(dbName != null) {
            this.dbName = dbName;
        }
        this.tables = new HashMap<String,Table>();
        this.autoNamespaceTables = false;
        this.packagePath = "my.package.path";
    }
    
    @XmlTransient
    public String getDbName() {
        return dbName;
    }
    
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    @XmlElement(name="entity")
    public List<Table> getTables() {
        List<Table> dbTables = new ArrayList<Table>(tables.values());
        return dbTables;
    }
    
    public void setTables(Collection<Table> tables) {
        this.tables = new HashMap<String,Table>();
        for(Table t : tables) {
            addTable(t);
        }
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void addTable(Table table) {
        tables.put(table.getName(),table);
    }
    
    public Table getTable(String name) {
        return tables.get(name);
    }
    
    @XmlTransient
    public String getPackagePath() {
        return packagePath;
    }
    
    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
    
    @XmlTransient
    public boolean isAutoNamespaceTables() {
        return autoNamespaceTables;
    }
    
    public void setAutoNameSpaceTables(boolean autoNameSpaceTables) {
        this.autoNamespaceTables = autoNameSpaceTables;
    }
    
    public String toServiceXML() {
        try {
            Marshaller marsh = JAXBContext.newInstance(Database.class).createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Set headers
            marsh.setProperty("com.sun.xml.bind.xmlHeaders",
                    "\n<!DOCTYPE service-builder PUBLIC "+
                    "\"-//Liferay//DTD Service Builder 6.1.0//EN\" "+
                    "\"http://www.liferay.com/dtd/liferay-service-builder_6_1_0.dtd\">");
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            marsh.marshal(this, bout);
            // Dirty hack.
            String ret = new String(bout.toByteArray()).replace(" standalone=\"yes\"?>","?>");
            return ret;
        } catch (JAXBException ex) {
            Logger.getLogger(DBImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Properties getDBDefaultProperties() {
        Properties props = new Properties() {
            @Override
            public synchronized Enumeration<Object> keys() {
                return Collections.enumeration(new TreeSet<Object>(super.keySet()));
            }
        };
        String prefix = getDbName();
        for(Table t : getTables()) {
            props.setProperty(prefix+"."+t.getName(), t.getName());
            props.setProperty(prefix+"."+t.getName()+".LOCALSERVICE", String.valueOf(t.isLocalService()));
            props.setProperty(prefix+"."+t.getName()+".REMOTESERVICE", String.valueOf(t.isRemoteService()));
            props.setProperty(prefix+"."+t.getName()+".FINDERS","");
            for(Column c : t.getColumns()) {
                props.setProperty(prefix+"."+t.getName()+"."+c.getName(), c.getName());
            }
            for(ForeignKey fk : t.getForeignKeys()) {
                props.setProperty(prefix+"."+t.getName()+"."+fk.getName(), "");
            }
        }
        return props;
    }
    
}
