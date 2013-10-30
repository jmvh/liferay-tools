/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db;

import fi.jmvh.liferay.db2servicexml.db.model.Database;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Jussi Hynninen
 */
public class DBImporter {
    
    private DBConnector connector;
    private Database db;
    
    public DBImporter(Properties properties) throws SQLException, IOException {
        connector = new DBConnector(properties);
        db = connector.getDatabase();
        connector.disconnect();
    }
    
    public Database getDB() {
        return db;
    }
    
}
