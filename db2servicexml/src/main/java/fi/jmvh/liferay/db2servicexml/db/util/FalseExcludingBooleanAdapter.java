/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Jussi Hynninen
 */
public class FalseExcludingBooleanAdapter extends XmlAdapter<Boolean, Boolean> {

    @Override
    public Boolean unmarshal(Boolean v) throws Exception {
        return Boolean.TRUE.equals(v);
    }

    @Override
    public Boolean marshal(Boolean v) throws Exception {
        if(v) { return v; }
        return null;
    }
    
}