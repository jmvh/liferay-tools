/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db;

import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Jussi Hynninen
 */
public class DBImporter {

    private DBConnector connector;
    
    public DBImporter(Properties properties) throws SQLException {
        connector = new DBConnector(properties);
        System.out.println(connector.getSchema());
        connector.disconnect();
    }
    
}
