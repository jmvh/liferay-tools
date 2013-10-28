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
    
    public Table(String name) {
        this.name = name;
        friendlyName = name;
        columns = new HashMap<String,Column>();
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

    public void setColumns(List<Column> columns) {
        this.columns = new HashMap<String,Column>();
        for(Column c : columns) {
            this.columns.put(c.getName(), c);
        }
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
    
}
