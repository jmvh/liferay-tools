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
public class Table {
    
    private String name;
    private String friendlyName;
    private Map<String,Column> columns;
    private boolean localService;
    private boolean remoteService;
    private Map<String,ForeignKey> foreignKeys;
    
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
                "\" remote-service=\""+isRemoteService()+"\">\n";
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
    
}
