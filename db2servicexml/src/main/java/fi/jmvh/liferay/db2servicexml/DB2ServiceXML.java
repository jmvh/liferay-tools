package fi.jmvh.liferay.db2servicexml;

import fi.jmvh.liferay.db2servicexml.db.util.DBImporter;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
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
            defaults.load(new BufferedInputStream(new FileInputStream(new File("default.properties"))));
        } catch (Exception ex) {
            Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        parseOptions(args);
        try {
            DBImporter importer = new DBImporter(defaults);
            writeToFile(
                    new File(defaults.getProperty("config.service-ext.file")),
                    importer.getServiceXML(),
                    false
                    );
            writeToFile(
                    new File(defaults.getProperty("config.hints-xml.filename")),
                    importer.getHintsXML(),
                    false
                    );
        } catch (SQLException ex) {
            Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void parseOptions(String[] args) {
        Options options = new Options();
        CommandLineParser parser = new PosixParser();
        options.addOption(new Option("d","jdbc.url",true,"JDBC URL for connecting to the DB"));
        options.addOption(new Option("c","jdbc.default.driver",true,"JDBC class name"));
        options.addOption(new Option("u","jdbc.user",true,"User name for the JDBC connection"));
        options.addOption(new Option("p","jdbc.password",true,"Password for the JDBC connection"));
        options.addOption(new Option("h","help",false,"Show this help"));
        options.addOption(new Option("C","jdbc.default.schema",true,"Default JDBC schema name (for PostgreSQL it's \"public\")"));
        options.addOption(new Option("s","create-config-skeleton",false,"Create a template file for defining friendly names for DB tables and columns"));
        options.addOption(new Option("S","config.skeleton.file",true,"File name for friendly names template"));
        options.addOption(new Option("o","config.service-ext.file",true,"File name for service.ext output"));
        options.addOption(new Option("D","data-source",true,"Spring data source (data-source param for entities)"));
        options.addOption(new Option("M","session-factory",true,"Spring session factory for the entities"));
        options.addOption(new Option("T","tx-manager",true,"Spring transaction manager for the entities"));
        options.addOption(new Option("N","package-path",true,"package-path attribute for the service-builder"));
        options.addOption(new Option("a","author",true,"Author name"));
        options.addOption(new Option("A","auto-namespace-tables",true,"package-path attribute for the service-builder"));
        
        try {
            CommandLine cmdLine = parser.parse(options,args);
            if(cmdLine.hasOption("help")) {
                printHelp(options);
            }
            
            for(Option o : cmdLine.getOptions()) {
                options.addOption(o);
            }
            
            for(Object o : options.getOptions()) {
                Option opt = (Option)o;
                if(opt.getValue() != null) {
                    defaults.put(opt.getLongOpt(),opt.getValue());
                }
            }
            
            if(cmdLine.hasOption("create-config-skeleton")) {
                createDbPropertiesFile();
            }
        } catch (ParseException ex) {
            printHelp(options);
        }
    }
    
    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "b2servicexml.sh [options]", options );
        System.exit(1);
    }
    
    private static void createDbPropertiesFile() {
        try {
            Properties props = new DBImporter(defaults).getDB().getDBDefaultProperties();
            Properties dbProperties = new Properties();
            if(defaults.containsKey("config.skeleton.file")) {
                File propsFile;
                try {
                    propsFile = new File(defaults.getProperty("config.skeleton.file"));
                    try {
                        dbProperties.load(new BufferedInputStream(new FileInputStream(propsFile)));
                    } catch(Exception e) {
                        // Just let this fail
                    }
                    // Persist already made customizations
                    for(Object p : props.keySet()) {
                        if(dbProperties.containsKey(p)) {
                            props.setProperty(p.toString(), dbProperties.getProperty(p.toString()));
                        }
                    }
                    if(propsFile.exists()) {
                        confirm("File "+propsFile.getName()+" already exists, do you want to continue");
                    }
                    props.store(new FileOutputStream(propsFile), getDefaultPropertiesHelp());
                } catch (Exception ex) {
                    Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.INFO, "Could not persist DB defaults:,{0}", ex);
                    System.exit(1);
                }
            }
            System.exit(0);
        } catch (SQLException ex) {
            Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void confirm(String question) {
        Scanner s = new Scanner(System.in);
        System.out.print(question+" (y/n)? ");
        if(!s.next().equals("y")) {
            System.exit(1);
        }
    }
    
    private static void writeToFile(File file, String input, boolean confirmOverwrite) {
        if(confirmOverwrite && file.exists()) {
            confirm("File "+file.getName()+" already exists, do you want to continue");
        }
        try {
            BufferedReader br = new BufferedReader(new StringReader(input));
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String line = br.readLine();
            while(line != null) {
                bw.write(line);
                bw.newLine();
                line = br.readLine();
            }
            bw.close();
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(DB2ServiceXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static  String getDefaultPropertiesHelp() {
        String ret = "";
        ret += "DB defaults file format:\n";
        ret += "# Friendly name for a table entity:\n";
        ret += "# <dbname>.<tablename>=TableFriendlyName\n";
        ret += "# Finder columns for table as a comma-separated list\n";
        ret += "# <dbname>.<tablename>.FINDERS=column1,column2\n";
        ret += "# Publish entity as a local service (true/false, default true)?\n";
        ret += "# <dbname>.<tablename>.LOCALSERVICE=true\n";
        ret += "# Publish entity as a remote service (true/false, default false)?\n";
        ret += "# <dbname>.<tablename>.REMOTESERVICE=false\n";
        ret += "# Friendly name for a column:\n";
        ret += "# <dbname>.<tablename>.<columnname>=ColumnFriendlyName\n";
        return ret;
        
    }
    
}