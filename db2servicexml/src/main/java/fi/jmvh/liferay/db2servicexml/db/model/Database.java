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
    
    public String getFriendlyNamesSkeleton() {
        String ret = "";
        String prefix = getDbName();
        //ret += "# Friendly name for database "+this.getDbName()+"\n";
        //ret += prefix+"="+this.getDbName()+"\n";
        for(Table t : getTables()) {
            ret += "# Friendly name for table "+t.getName()+"\n";
            ret += prefix+"."+t.getName()+"="+t.getFriendlyName()+"\n";
            ret += prefix+"."+t.getName()+".LOCALSERVICE="+t.isLocalService()+"\n";
            ret += prefix+"."+t.getName()+".REMOTESERVICE="+t.isRemoteService()+"\n";
            ret += "# Finders for table "+t.getName()+" (comma separated list of column names)\n";
            ret += prefix+"."+t.getName()+".FINDERS=\n";
            ret += "# Friendly names for columns in "+t.getName()+"\n";
            for(Column c : t.getColumns()) {
                ret += prefix+"."+t.getName()+"."+c.getName()+"="+c.getFriendlyName()+"\n";
            }
            for(ForeignKey fk : t.getForeignKeys()) {
                ret += prefix+"."+t.getName()+"."+fk.getName()+"="+fk.getFriendlyName()+"\n";
            }
        }
        return ret;
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
            // No need to set the defaults yet, these will be overwritten anyway
            props.setProperty(prefix+"."+t.getName(), "");
            props.setProperty(prefix+"."+t.getName()+".LOCALSERVICE", "");
            props.setProperty(prefix+"."+t.getName()+".REMOTESERVICE", "");
            props.setProperty(prefix+"."+t.getName()+".FINDERS","");
            for(Column c : t.getColumns()) {
                props.setProperty(prefix+"."+t.getName()+"."+c.getName(), "");
            }
            for(ForeignKey fk : t.getForeignKeys()) {
                props.setProperty(prefix+"."+t.getName()+"."+fk.getName(), "");
            }
        }
        return props;
    }
    
}
