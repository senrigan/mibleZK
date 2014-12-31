package com.gdc.nms.web.mibquery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class testC {

   
    /*
    public static void main(String [] args) {
	Testb b=new Testb();
	ArrayList<TestAbs> oid=new ArrayList<TestAbs>();
	oid.add(b);
	b.setOID(oid);
	System.out.println(b.getOID());
    }
   */
    public static void main(String []args) {
	// TODO Auto-generated method stub
	DeviceFileManager dv=new DeviceFileManager();
	ArrayList<DeviceControllerManager> dvc=new ArrayList<DeviceControllerManager>();
	dv.writeDevice(dvc);
    }
    /*public static void main(String args[]) {
	//XStream xstream = new XStream();
	XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
	//XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6

	xstream.alias("person", Person.class);
	xstream.alias("phonenumber", PhoneNumber.class);
	Person joe = new Person("Joe", "Walnes");
	joe.setPhone(new PhoneNumber(123, "1234-456"));
	joe.setFax(new PhoneNumber(123, "9999-999"));
	String xml = xstream.toXML(joe);
	System.out.println("xml"+xml);
	try {
	    FileUtils.writeStringToFile(new File("DataFile/device.xml"), xml);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	//Person newJoe = (Person)xstream.fromXML(xml);
	//System.out.println(""+newJoe);
	
	FileManager fm=FileManager.getInstance();
	System.out.println("xml 555");
	 xml=fm.getXML();
	//System.out.println("xmll"+xml);
	Person newJoe=(Person)xstream.fromXML(xml);
	System.out.println(""+newJoe);
	
    }*/
   
    
}