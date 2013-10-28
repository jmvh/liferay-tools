/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db;

import fi.jmvh.liferay.db2servicexml.db.model.Column;
import fi.jmvh.liferay.db2servicexml.db.model.Database;
import fi.jmvh.liferay.db2servicexml.db.model.Table;
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
        Database db = connector.getDatabase();
        connector.disconnect();
        
        System.out.println("<namespace>"+db.getDbName()+"</namespace>");
        for(Table t : db.getTables()) {
            System.out.println();
            System.out.println("<entity name=\""+t.getFriendlyName()+"\" table=\""+t.getName()+"\" local-service=\"true\" remote-service=\"false\">");
            System.out.println();
            for(Column c : t.getColumns()) {
                System.out.println("\t<column name=\""+c.getFriendlyName()+"\" db-name=\""+c.getName()+"\" type=\""+c.getType()+"\" />");        
            }
            System.out.println();
            System.out.println("</entity>");
        }
        System.out.println();
        System.out.println("</table>");
    }
    
}
