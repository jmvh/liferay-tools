/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model.hints;

import fi.jmvh.liferay.db2servicexml.db.model.Column;
import fi.jmvh.liferay.db2servicexml.db.model.Table;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Jussi Hynninen
 */
@XmlType(name="model")
class Model {
    
    @XmlTransient
    private List<Field> columns;
    @XmlAttribute(name="name")
    private String name;
    
    public Model() {
        init(null,null);
    }
    
    public Model(Table t,String pkgPath) {
        init(t,pkgPath);
        t.getFriendlyName();
        for(Column c : t.getColumns()) {
            this.addColumn(c);
        }
    }
    
    private void init(Table t, String pkgPath) {
        columns = new ArrayList<Field>();
        this.name  = pkgPath+".model."+t.getFriendlyName();
    }
    
    
    @XmlElement(name="field")
    public List<Field> getColumns() {
        return columns;
    }

    public void setColumns(List<Field> columns) {
        this.columns = columns;
    }

    private void addColumn(Column c) {
        columns.add(new Field(c));
    }
}
