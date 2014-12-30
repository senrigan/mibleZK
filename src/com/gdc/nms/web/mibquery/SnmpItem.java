package com.gdc.nms.web.mibquery;

public abstract class SnmpItem implements SnmpElement {
    private String name;
    private String oid;
    private String description;
    private SNMPType snmpType;
    private DataType dataType;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOID() {
        return oid;
    }
    public void SetOID(String oid) {
        this.oid = oid;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public SNMPType getSnmpType() {
        return snmpType;
    }
    public void setSnmpType(SNMPType snmpType) {
        this.snmpType = snmpType;
    }
    public DataType getDataType() {
        return dataType;
    }
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    

}
