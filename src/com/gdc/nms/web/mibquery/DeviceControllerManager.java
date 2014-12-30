package com.gdc.nms.web.mibquery;

import java.sql.Date;
import java.util.ArrayList;

public class DeviceControllerManager {
    private ArrayList<SnmpElement>itemOID;
    private String 	sysoOID;
    private InfoDevice 	info;
    private Date	dateCreate;
    
  

    public ArrayList<SnmpElement> getItemOID() {
        return itemOID;
    }

    public void setItemOID(ArrayList<SnmpElement> itemOID) {
        this.itemOID = itemOID;
    }

    public InfoDevice getInfo() {
        return info;
    }

    public void setInfo(InfoDevice info) {
        this.info = info;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
    
    public String getSysoOID() {
  	return this.sysoOID;
      }

    public void setSysoOID(String sysoOID) {
        this.sysoOID = sysoOID;
    }
}
