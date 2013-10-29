/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db;

import fi.jmvh.liferay.db2servicexml.db.model.Column;
import fi.jmvh.liferay.db2servicexml.db.model.Database;
import fi.jmvh.liferay.db2servicexml.db.model.Table;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Jussi Hynninen
 */
public class DBConnector {
    
    private Connection con;
    private Properties dbProperties;
    private Properties friendlyNames;
    
    public DBConnector(Properties dbProperties) throws SQLException {
        this.dbProperties = dbProperties;
        friendlyNames = new Properties();
        if(this.dbProperties.containsKey("config.skeleton.file")) {
            try {
                friendlyNames.load(new FileInputStream(new File(dbProperties.getProperty("config.skeleton.file"))));
            } catch (Exception ex) {
                // Logger.getLogger(DBConnector.class.getName()).log(Level.INFO, "Could not load "+dbProperties.getProperty("config.skeleton.file")+", ignoring...");
            }
        }
        con = DriverManager.getConnection(
                dbProperties.getProperty("jdbc.url"),
                dbProperties.getProperty("jdbc.user"),
                dbProperties.getProperty("jdbc.password")
                );
    }
    
    public Database getDatabase() throws SQLException {
        Database db = new Database(con.getCatalog());
        DatabaseMetaData meta = con.getMetaData();
        List<Table> tables = getTables(meta,db.getDbName());
        for(Table table : tables) {
            List<Column> columns = getColumns(meta,db.getDbName(),table.getName());
            for(Column column : columns) {
                table.addColumn(column);
            }
            db.addTable(table);
        }
        return db;
    }
    
    private List<Column> getColumns(DatabaseMetaData meta, String dbName, String table) throws SQLException {
        ArrayList<Column> res = new ArrayList<Column>();
        ResultSet columns = meta.getColumns(null,null,table,null);
        while(columns.next() && columns.getRow() <= columns.getMetaData().getColumnCount()) {
            Column column = new Column(columns.getString("COLUMN_NAME"),columns.getString("TYPE_NAME"));
            column.setFriendlyName(friendlyNames.getProperty(dbName+"."+table+"."+column.getName(),column.getName()));
            res.add(column);
        }
        
        return res;
    }
    
    private List<Table> getTables(DatabaseMetaData meta, String dbName) throws SQLException {
        ArrayList<Table> res = new ArrayList<Table>();
        String type[] = {"TABLE"};
        ResultSet tables = meta.getTables(null, dbProperties.getProperty("jdbc.default.schema"), null,type);
        while (tables.next()) {
            String tableName = tables.getString(3);
            Table tab = new Table(tableName);
            tab.setLocalService(Boolean.parseBoolean(friendlyNames.getProperty(dbName+"."+tableName+".LOCALSERVICE",tab.isLocalService()+"")));
            tab.setRemoteService(Boolean.parseBoolean(friendlyNames.getProperty(dbName+"."+tableName+".REMOTESERVICE",tab.isRemoteService()+"")));
            tab.setFriendlyName(friendlyNames.getProperty(dbName+"."+tableName,tableName));
            res.add(tab);
        }
        return res;
    }
    
    public void disconnect() throws SQLException {
        con.close();
    }
    
}
