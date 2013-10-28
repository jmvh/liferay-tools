/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Jussi Hynninen
 */
public class DBConnector {
    
    private Connection con;
    
    public DBConnector(Properties dbProperties) throws SQLException {
        con = DriverManager.getConnection(
                dbProperties.getProperty("jdbc.url"),
                dbProperties.getProperty("jdbc.username"),
                dbProperties.getProperty("jdbc.password")
                );
    }
    
    public String getSchema() throws SQLException {
        return con.getSchema();
    }
    
    public void disconnect() throws SQLException {
        con.close();
    }
    
}
