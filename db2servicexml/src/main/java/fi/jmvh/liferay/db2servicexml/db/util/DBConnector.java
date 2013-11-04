/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.util;

import fi.jmvh.liferay.db2servicexml.db.model.Column;
import fi.jmvh.liferay.db2servicexml.db.model.Database;
import fi.jmvh.liferay.db2servicexml.db.model.ForeignKey;
import fi.jmvh.liferay.db2servicexml.db.model.Table;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
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
                friendlyNames.load(new BufferedInputStream(new FileInputStream(new File(dbProperties.getProperty("config.skeleton.file")))));
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
            table.setDataSource(dbProperties.getProperty("data-source",null));
            table.setTxManager(dbProperties.getProperty("tx-manager",null));
            table.setSessionFactory(dbProperties.getProperty("session-factory",null));
            db.addTable(table);
            addIndexes(table);
        }
        // Foreign keys can only be added after all the tables have been constructed
        /*
        for(Table table : tables) {
            addForeignKeys(db, table);
        }
        */
        return db;
    }
    
    private List<Column> getColumns(DatabaseMetaData meta, String dbName, String table) throws SQLException {
        ArrayList<Column> res = new ArrayList<Column>();
        ResultSet columns = meta.getColumns(null,null,table,null);
        ResultSet primaryKeys = meta.getPrimaryKeys(null, null, table);
        HashSet<String> keys = new HashSet<String>();
        while(primaryKeys.next()) {
            keys.add(primaryKeys.getString("COLUMN_NAME"));
        }
        while(columns.next() && columns.getRow() <= columns.getMetaData().getColumnCount()) {
            String cName = columns.getString("COLUMN_NAME");
            boolean primary = false;
            if(keys.contains(cName)) {
                primary = true;
            }
            Column column = new Column(cName,columns.getString("TYPE_NAME"),primary);
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
    
    /**
     * Adds
     * @param db
     * @param table
     * @throws SQLException
     */
    private void addForeignKeys(Database db, Table table) throws SQLException {
        ResultSet fKeys = con.getMetaData().getImportedKeys(null, null, table.getName());
        while(fKeys.next()) {
            String fkName = fKeys.getString("FK_NAME");
            Table fkTable = db.getTable(fKeys.getString("PKTABLE_NAME"));
            ForeignKey fk = new ForeignKey(
                    fkName,
                    fkTable.getFriendlyName(),
                    fkTable.getColumn(fKeys.getString("PKCOLUMN_NAME")).getFriendlyName()
                    );
            table.addForeignKey(fk);
        }
    }
    
    private void addIndexes(Table table) throws SQLException {
        ResultSet indexes = con.getMetaData().getIndexInfo(null, null, table.getName(),false,false);
        while(indexes.next()) {
            table.getColumn(indexes.getString("COLUMN_NAME")).setPrimaryKey(true);
        }
    }
}
