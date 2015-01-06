package com.gdc.nms.web.mibquery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DeviceFileManager {
	private FileManager file;
    public ArrayList<DeviceControllerManager> getDevice() {
    	String xml;
    	file=FileManager.getInstance();
    	xml=file.getXML();
    	XStream xstream = getXMLConfiguration();
    	ControllerManager manager=(ControllerManager)xstream.fromXML(xml);
    	System.out.println("devices"+manager.getDevices());
	return manager.getDevices();
    }
    public ArrayList<DeviceControllerManager> searchDevice(ArrayList<DeviceControllerManager> device){
	return null;
    }
    public static XStream getXMLConfiguration(){
    	XStream xstream = new XStream(new DomDriver());
    	xstream.alias("Devicescontrollers", ControllerManager.class);
    	xstream.alias("InfoDevice",InfoDevice.class);
    	xstream.alias("DateCreation", Date.class);
    	xstream.alias("DeviceItem", DeviceControllerManager.class);
    	xstream.processAnnotations(DeviceControllerManager.class);
    	xstream.aliasField("DeviceList", ControllerManager.class, "deviceList"); 
    	xstream.aliasField("SNMP", DeviceControllerManager.class, "itemOID");
    	xstream.alias("SNMPItem", SnmpItem.class);
    	return xstream;
    }
    public void writeDevice(ArrayList<DeviceControllerManager> devices) {
	ControllerManager ctn=new ControllerManager();
	ctn.setDevices(devices);
	
	XStream xstream = getXMLConfiguration();
	
	
	
	String xml = xstream.toXML(ctn);
	file=FileManager.getInstance();
	file.saveXML(xml);
	
    }
}
