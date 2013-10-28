/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db;

import fi.jmvh.liferay.db2servicexml.db.model.Column;
import fi.jmvh.liferay.db2servicexml.db.model.Database;
import fi.jmvh.liferay.db2servicexml.db.model.Table;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
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
    
    public DBConnector(Properties dbProperties) throws SQLException {
        this.dbProperties = dbProperties;
        con = DriverManager.getConnection(
                dbProperties.getProperty("jdbc.url"),
                dbProperties.getProperty("jdbc.user"),
                dbProperties.getProperty("jdbc.password")
                );
    }
    
    public Database getDatabase() throws SQLException {
        Database db = new Database();
        DatabaseMetaData meta = con.getMetaData();
        List<String> tables = getTables(meta);
        for(String tableName : tables) {
            Table table = new Table(tableName);
            List<String> columns = getColumns(meta,tableName);
            for(String column : columns) {
                table.addColumn(new Column(column,"String"));
            }
            db.addTable(table);
        }
        return db;
    }
    
    private List<String> getColumns(DatabaseMetaData meta, String table) throws SQLException {
        ArrayList<String> res = new ArrayList<String>();
        ResultSet columns = meta.getColumns(null,null,table,null);
        
        while(columns.next()) {
            String column = columns.getString(4);
            res.add(column);
        }
        return res;
    }
    
    private List<String> getTables(DatabaseMetaData meta) throws SQLException {
        ArrayList<String> res = new ArrayList<String>();
        String type[] = {"TABLE"};
        ResultSet tables = meta.getTables(null, dbProperties.getProperty("jdbc.default.schema"), null,type);
        while (tables.next()) {
            String tableName = tables.getString(3);
            res.add(tableName);
        }
        return res;
    }
    
    public void disconnect() throws SQLException {
        con.close();
    }
    
}
