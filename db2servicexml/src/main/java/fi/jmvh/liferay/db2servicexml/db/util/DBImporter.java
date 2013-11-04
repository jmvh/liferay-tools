/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.util;

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
        db.setAutoNameSpaceTables(
                Boolean.parseBoolean(
                    properties.getProperty(
                        "auto-namespace-tables",
                        Boolean.toString(db.isAutoNamespaceTables())
                    )
                )
        );
        db.setPackagePath(properties.getProperty("package-path", db.getPackagePath()));
    }
    
    public Database getDB() {
        return db;
    }
    
    public String getServiceXML() {
        return db.toServiceXML();
    }
    
}
