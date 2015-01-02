package com.gdc.nms.web.mibquery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
	ArrayList<SnmpElement> elm=new ArrayList<SnmpElement>();
	Date date=new Date();
	String oid="1.3.4.56";
	InfoDevice inf=new InfoDevice(new Company(157,new String ("cisco")), new Model(new String ("1.3.5.6.7"),new String ("1800"),new String("quien sabe")));
	DeviceControllerManager x=new DeviceControllerManager(elm,oid,inf,date);
//	y.setDateCreate(date);
	inf=new InfoDevice(new Company(157,new String ("cisscos")), new Model(new String ("1.3.5.6.7.9.9"),new String ("1999"),new String("quien sabe que")));
	oid="57.8389.82877.889989";
	date=new Date();
	 elm=new ArrayList<SnmpElement>();
	 SnmpElement snmp=new Escalar();
	 snmp.setAcces("hola");
	 snmp.setDataType(DataType.BITSTRING);
	 snmp.setDescription("hola k hace");
	 snmp.SetOID("1.2.3.4.58.6");
	 snmp.setSnmpType(SNMPType.COLUMN);
	 elm.add(snmp);
	 snmp=new Escalar();
	 snmp.setAcces("hola");
	 snmp.setDataType(DataType.BITSTRING);
	 snmp.setDescription("hola k hace");
	 snmp.SetOID("1.2.3.4.58.6");
	 snmp.setSnmpType(SNMPType.COLUMN);
	 elm.add(snmp);
	DeviceControllerManager y=new DeviceControllerManager(elm,oid,inf,date);
	
	dvc.add(x);
	dvc.add(y);
	
	System.out.println("dvc"+dvc);
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