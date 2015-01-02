package com.gdc.nms.web.mibquery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DeviceFileManager {
    public ArrayList<DeviceControllerManager> getDevice() {
	return null;
    }
    public ArrayList<DeviceControllerManager> searchDevice(ArrayList<DeviceControllerManager> device){
	return null;
    }
    
    public void writeDevice(ArrayList<DeviceControllerManager> devices) {
	ControllerManager ctn=new ControllerManager();
	ctn.setDevices(devices);
	System.out.println("sice "+ctn.getDevices().size());
	
	XStream xstream = new XStream(new DomDriver());
	xstream.alias("controllers", ControllerManager.class);
	xstream.alias("DeviceController", DeviceControllerManager.class);
	xstream.alias("InfoDevice",InfoDevice.class);
	xstream.alias("DateCreation", Date.class);
	xstream.aliasAttribute("fecha", "dateCreate");
	//xstream.alias("DevicesControllers", ArrayList.class);
	 //xstream.addImplicitCollection(SnmpElement.class, "itemOID");
	// xstream.alias("OIDELEMENT", SnmpElement.class);
	//xstream.addImplicitCollection(DeviceControllerManager.class, "itemOID", SnmpElement.class);
	xstream.addImplicitCollection(DeviceControllerManager.class, "itemOID", "SNMP", SnmpElement.class);
	//xstream.addImplicitCollection(ControllerManager.class, "devices", DeviceControllerManager.class);
	xstream.processAnnotations(DeviceControllerManager.class);
	 String xml = xstream.toXML(ctn);
	System.out.println("xml devices"+xml);
    }
}
