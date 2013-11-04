/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model.hints;

import fi.jmvh.liferay.db2servicexml.db.model.Database;
import fi.jmvh.liferay.db2servicexml.db.model.Table;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jussi Hynninen
 */
@XmlRootElement(name="model-hints")
public class Hints {
    
    @XmlTransient
    public List<Model> tables;
    
    public Hints() {
        init();
    }
    public Hints(Database db) {
        init();
        for(Table t : db.getTables()) {
            addTable(t,db.getPackagePath());
        }
    }
    
    private void init() {
        this.tables = new ArrayList<Model>();
    }
    
    @XmlElement(name="model")
    public List<Model> getTables() {
        return tables;
    }
    
    private void addTable(Table t, String pkgPath) {
        tables.add(new Model(t,pkgPath));
    }
    
    public void setTables(List<Model> tables) {
        this.tables = tables;
    }
    
    public String toHintsXML() {
        try {
            Marshaller marsh = JAXBContext.newInstance(Hints.class).createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marsh.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marsh.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\"?>\n");
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            marsh.marshal(this, bout);
            // Dirty hack.
            //String ret = new String(bout.toByteArray()).replace(" standalone=\"yes\"?>","?>");
            //ret = ret.replace(ret, ret)
            return new String(bout.toByteArray());
        } catch (JAXBException ex) {
            Logger.getLogger(Hints.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
