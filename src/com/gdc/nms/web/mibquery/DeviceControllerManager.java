package com.gdc.nms.web.mibquery;

import java.util.Date;
import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class DeviceControllerManager {
  
    private ArrayList<SnmpElement>itemOID;
    private String 	sysoOID;
    private InfoDevice 	info;
    private Date	dateCreate;
    private DeviceConfigurationController configuration;
    public DeviceControllerManager() {
    
    }
    @Override
    public String toString() {
	return "DeviceControllerManager [itemOID=" + itemOID + ", sysoOID="
		+ sysoOID + ", info=" + info + ", dateCreate=" + dateCreate
		+ "]";
    }
    public DeviceControllerManager(ArrayList<SnmpElement> itemOID,String sysoOID,InfoDevice info,Date dateCreate) {
	this.itemOID=itemOID;
	this.sysoOID=sysoOID;
	this.info=info;
	this.dateCreate=dateCreate;
	
    }

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
