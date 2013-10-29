/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Jussi Hynninen
 */
public class Database {
    
    private String dbName = "MyDatabase";
    private HashMap<String,Table> tables;
    //private List<Table> tables;
    
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
        this.tables = new HashMap<String,Table>();
    }
    
    public String getDbName() {
        return dbName;
    }
    
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    public Collection<Table> getTables() {
        return tables.values();
    }
    
    public void setTables(Collection<Table> tables) {
        this.tables = new HashMap<String,Table>();
        for(Table t : tables) {
            addTable(t);
        }
    }
    
    public void addTable(Table table) {
        tables.put(table.getName(),table);
    }
    
    public Table getTable(String name) {
        return tables.get(name);
    }
    
    public String toServiceXML() {
        String ret = "<namespace>"+this.getDbName()+"</namespace>\n";
        for(Table t : this.getTables()) {
            ret += "\n";
            ret += t.toServiceXML();
        }
        ret += "\n";
        
        return ret;
    }
    
    public String getFriendlyNamesSkeleton() {
        String ret = "";
        String prefix = this.getDbName();
        //ret += "# Friendly name for database "+this.getDbName()+"\n";
        //ret += prefix+"="+this.getDbName()+"\n";
        for(Table t : this.getTables()) {
            ret += "# Friendly name for table "+t.getName()+"\n";
            ret += prefix+"."+t.getName()+"="+t.getFriendlyName()+"\n";
            ret += prefix+"."+t.getName()+".LOCALSERVICE="+t.isLocalService()+"\n";
            ret += prefix+"."+t.getName()+".REMOTESERVICE="+t.isRemoteService()+"\n";
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
    
}
