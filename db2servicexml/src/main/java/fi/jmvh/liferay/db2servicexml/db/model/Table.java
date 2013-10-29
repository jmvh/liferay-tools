/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    
    public Table(String name) {
        this.name = name;
        friendlyName = name;
        columns = new HashMap<String,Column>();
        localService = true;
        remoteService = false;
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
        this.columns.put(column.getName(),column);
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
    
}
