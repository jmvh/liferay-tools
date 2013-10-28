/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jmvh.liferay.db2servicexml.db.model;

/**
 *
 * @author Jussi Hynninen
 */
public class Column {
    
    private String name;
    private String friendlyName;
    private boolean idx;
    private String type;

    public Column(String name, String type) {
        init(name,type,false);
    }
    
    public Column(String name, String type, boolean idx) {
        init(name,type,idx);
    }
    
    private void init(String name, String type, boolean idx) {
        this.name = name;
        friendlyName = name;
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIdx() {
        return idx;
    }

    public void setIdx(boolean idx) {
        this.idx = idx;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getType() {
        return this.type;
    }
    
}
