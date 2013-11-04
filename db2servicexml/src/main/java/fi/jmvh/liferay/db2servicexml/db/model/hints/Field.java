/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model.hints;

import fi.jmvh.liferay.db2servicexml.db.model.Column;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Jussi Hynninen
 */
@XmlType(name="field")
class Field {
    @XmlAttribute(name="name")
    private String name;
    @XmlAttribute(name="type")
    private String type;
    @XmlAttribute(name="length")
    private int length;

    public Field() { }
    
    public Field(Column c) {
        this.name = c.getFriendlyName();
        this.type = c.getJavaType();
        this.length = c.getLength();
    }
}
