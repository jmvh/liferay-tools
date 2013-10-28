package fi.jmvh.liferay.db2servicexml;

import fi.jmvh.liferay.db2servicexml.db.DBImporter;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Hello world!
 *
 */
public class DB2ServiceXML {
    
    private static Properties defaults;
    
    public static void main( String[] args ) {
        defaults = new Properties();
        try {
            defaults.load(new FileInputStream(new File("default.properties")));
        } catch (Exception ex) {
            Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        Options options = parseOptions(args);
        for(Object o : options.getOptions()) {
            Option opt = (Option)o;
            if(opt.getValue() != null) {
                defaults.put(opt.getLongOpt(),opt.getValue());
            }
        }
        try {
            DBImporter importer = new DBImporter(defaults);
        } catch (SQLException ex) {
            Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Options parseOptions(String[] args) {
        Options options = new Options();
        CommandLineParser parser = new PosixParser();
        options.addOption(new Option("d","jdbc.url",true,"JDBC URL for connecting to the DB"));
        options.addOption(new Option("c","jdbc.default.driver",true,"JDBC class name"));
        options.addOption(new Option("u","jdbc.user",true,"User name for the JDBC connection"));
        options.addOption(new Option("p","jdbc.password",true,"Password for the JDBC connection"));
        options.addOption(new Option("h","help",false,"Show this help"));
        try {
            CommandLine cmdLine = parser.parse(options,args);
            if(cmdLine.hasOption("help")) {
                printHelp(options);
            }
            for(Option o : cmdLine.getOptions()) {
                options.addOption(o);
            }
        } catch (ParseException ex) {
            printHelp(options);
        }
        return options;
    }
    
    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "b2servicexml.sh [options]", options );
        System.exit(1);
    }

}