/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jussi Hynninen
 */
public class Database {
    
    private String dbName = "MyDatabase";
    private List<Table> tables;
    
    public Database() {
        init(null);
    }
    
    public Database(String dbName) {
        init(dbName);
    }
    
    private void init(String dbName) {
        if(dbName != null) {
            this.dbName = dbName;
        }
        this.tables = new ArrayList<Table>();
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
    
    public void addTable(Table table) {
        tables.add(table);
    }
    
}
