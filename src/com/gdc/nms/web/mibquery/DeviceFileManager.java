package com.gdc.nms.web.mibquery;

import java.util.ArrayList;

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
	XStream xstream = new XStream(new DomDriver());
	xstream.alias("DeviceController", DeviceControllerManager.class);
	String xml = xstream.toXML(devices);
	System.out.println("xml devices"+xml);
    }
}
