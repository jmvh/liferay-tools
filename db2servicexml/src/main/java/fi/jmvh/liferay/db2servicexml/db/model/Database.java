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
    
    public String toServiceXML() {
        String ret = "<namespace>"+this.getDbName()+"</namespace>\n";
        for(Table t : this.getTables()) {
            ret += "\n";
            ret += "<entity name=\""+t.getFriendlyName()+"\" table=\""+t.getName()+"\" local-service=\""+t.isLocalService()+"\" remote-service=\""+t.isRemoteService()+"\">\n";
            ret += "\n";
            for(Column c : t.getColumns()) {
                ret += "\t<column name=\""+c.getFriendlyName()+"\" db-name=\""+c.getName()+"\" type=\""+c.getType()+"\"";
                if(c.isPrimaryKey()) {
                    ret += " primary=\"true\"";
                }
                ret += " />\n";
            }
            ret += "\n";
            ret += "</entity>\n";
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
        }
        return ret;
    }
    
}
