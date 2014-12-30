package com.gdc.nms.web.mibquery;

import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.browser.MibNode;
import net.percederberg.mibble.snmp.SnmpAccess;
import net.percederberg.mibble.snmp.SnmpObjectType;
import net.percederberg.mibble.value.ObjectIdentifierValue;


public class InfoOID {
    private ObjectIdentifierValue oid ;
    private MibType typeOID;
    private SnmpAccess acces;
    private String 	description;
    private Type	typeSnmp;
    
   
    public InfoOID(MibNode mib) {
	ObjectIdentifierValue 	OID;
	MibValueSymbol 		mvs;
	OID=mib.getValue();
	setOid(OID);
	
	mvs=OID.getSymbol();
	setTypeOID(mvs.getType());
	if(mvs.getType() instanceof SnmpObjectType) {
	    SnmpObjectType snmp =(SnmpObjectType) mvs.getType();
	    setAcces(snmp.getAccess());
	}else {
	    setAcces(null);
	}
	
	if(mvs.isTable()) {
	    setTypeSnmp(Type.TABLE);
	}else if(mvs.isTableColumn()) {
	    setTypeSnmp(Type.COLUMN);
	}else if(mvs.isTableRow()) {
	    setTypeSnmp(Type.ROW);
	}else if(mvs.isScalar()) {
	    setTypeSnmp(Type.ESCALAR);
	}
	
	setDescription(mib.getDescription());
    }

    public ObjectIdentifierValue getOid() {
        return oid;
    }

    public void setOid(ObjectIdentifierValue oid) {
        this.oid = oid;
    }

    public MibType getTypeOID() {
        return typeOID;
    }

    public void setTypeOID(MibType typeOID) {
        this.typeOID = typeOID;
    }

    public SnmpAccess getAcces() {
        return acces;
    }

    public void setAcces(SnmpAccess acces) {
        this.acces = acces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setTypeSnmp(Type typeSnmp) {
        this.typeSnmp = typeSnmp;
    }

    

    
    
    public enum Type {
	ESCALAR,
	TABLE,
	COLUMN,
	ROW;
    }
    
    
}
