/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Jussi Hynninen
 */
@XmlType(name="entity")
public class Table { //implements Comparable<Table> {
    
    @XmlAttribute(name="table")
    private String name;
    @XmlAttribute(name="name")
    private String friendlyName;
    private Map<String,Column> columns;
    @XmlAttribute(name="local-service")
    private boolean localService;
    @XmlAttribute(name="remote-service")
    private boolean remoteService;
    private Map<String,ForeignKey> foreignKeys;
    @XmlAttribute(name="data-source")
    private String dataSource;
    @XmlAttribute(name="session-factory")
    private String sessionFactory;
    @XmlAttribute(name="tx-manager")
    private String txManager;
    
    public Table() { };
    
    public Table(String name) {
        this.name = name;
        friendlyName = name;
        columns = new HashMap<String,Column>();
        localService = true;
        remoteService = false;
        foreignKeys = new HashMap<String,ForeignKey>();
    }
    
    @XmlTransient
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @XmlElement(name="column")
    public Collection<Column> getColumns() {
        return columns.values();
    }
    
    public void addColumn(Column column) {
        columns.put(column.getName(),column);
    }
    
    public Column getColumn(String name) {
        return columns.get(name);
    }
    
    @XmlTransient
    public String getFriendlyName() {
        return friendlyName;
    }
    
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
    
    @XmlTransient
    public boolean isLocalService() {
        return localService;
    }
    
    public void setLocalService(boolean localService) {
        this.localService = localService;
    }
    
    @XmlTransient
    public boolean isRemoteService() {
        return remoteService;
    }
    
    public void setRemoteService(boolean remoteService) {
        this.remoteService = remoteService;
    }
    
    public Collection<ForeignKey> getForeignKeys() {
        return foreignKeys.values();
    }
    
    public void setForeignKeys(Map<String, ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
    
    public void addForeignKey(ForeignKey fk) {
        foreignKeys.put(fk.getName(), fk);
    }
    
    /*
     * A table that is referred to in another table has to be defined before
     * the referring table (a referred table is "larger" than the referring one)
     */
    /*
     * public int compareTo(Table t) {
     * if(t.refersTo(this)) {
     * return 1;
     * }
     * if(this.refersTo(t)) {
     * return -1;
     * }
     * return 0;
     * }
     * 
     * private boolean refersTo(Table t) {
     * if(!foreignKeys.isEmpty()) {
     * for(ForeignKey fk : foreignKeys.values()) {
     * if(fk.getFkTable().equals(t.getFriendlyName())) {
     * return true;
     * }
     * }
     * }
     * return false;
     * }
     */
    
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    
    public void setTxManager(String txManager) {
        this.txManager = txManager;
    }
    
    public void setSessionFactory(String sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
