/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jussi Hynninen
 */
public class Table { //implements Comparable<Table> {
    
    private String name;
    private String friendlyName;
    private Map<String,Column> columns;
    private boolean localService;
    private boolean remoteService;
    private Map<String,ForeignKey> foreignKeys;
    private String dataSource;
    private String sessionFactory;
    private String txManager;
    
    public Table(String name) {
        this.name = name;
        friendlyName = name;
        columns = new HashMap<String,Column>();
        localService = true;
        remoteService = false;
        foreignKeys = new HashMap<String,ForeignKey>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Column> getColumns() {
        return columns.values();
    }
    
    public void addColumn(Column column) {
        columns.put(column.getName(),column);
    }
    
    public Column getColumn(String name) {
        return columns.get(name);
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public boolean isLocalService() {
        return localService;
    }

    public void setLocalService(boolean localService) {
        this.localService = localService;
    }

    public boolean isRemoteService() {
        return remoteService;
    }

    public void setRemoteService(boolean remoteService) {
        this.remoteService = remoteService;
    }
    
    public String toServiceXML() {
        String ret = "";
        ret += "<entity name=\""+getFriendlyName()+
                "\" table=\""+getName()+"\" local-service=\""+isLocalService()+
                "\" remote-service=\""+isRemoteService()+"\"";
        if(dataSource != null) {
            ret += " data-source=\""+dataSource+"\"";
        }
        if(sessionFactory != null) {
            ret += " session-factory=\""+sessionFactory+"\"";
        }
        if(txManager != null) {
            ret += " tx-manager=\""+txManager+"\"";
        }
        ret += ">\n";
        ret += "\n";
        for(Column c : getColumns()) {
            ret += c.toServiceXML();
        }
        ret += "\n";
        for(ForeignKey fk : getForeignKeys()) {
            ret += fk.toServiceXML();
        }
        ret += "\n";
        ret += "</entity>\n";
        return ret;
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
    public int compareTo(Table t) {
        if(t.refersTo(this)) {
            return 1;
        }
        if(this.refersTo(t)) {
            return -1;
        }
        return 0;
    }

    private boolean refersTo(Table t) {
        if(!foreignKeys.isEmpty()) {
            for(ForeignKey fk : foreignKeys.values()) {
                if(fk.getFkTable().equals(t.getFriendlyName())) {
                    return true;
                }
            }
        }
        return false;
    }
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
