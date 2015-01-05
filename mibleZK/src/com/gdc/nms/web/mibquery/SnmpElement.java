package com.gdc.nms.web.mibquery;

public interface SnmpElement {
    public String getAcces();
    public String getName();
    public String getDescription();
    public String getOID();
    public SNMPType getSnmpType();
    public DataType getDataType();
    public void setAcces(String acces);
    public void setName(String name);
    public void setDescription(String description);
    public void SetOID(String oid);
    public void setSnmpType(SNMPType type);
    public void setDataType(DataType type);
}
