package com.gdc.nms.web.mibquery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class FileManager {
    //private String nameFile;
   
    private File dataDirectory;
    private static FileManager instance = null;
    
    private  FileManager() {
	if (instance == null) {
	    String 		dirPath="DataFile";
	   
	    dataDirectory=new File(dirPath);
	}
	
    }
    
    public static FileManager getInstance() {
	if(instance==null) {
	    instance=new FileManager();
	}
	return instance;
    }
    
    public File getMibBase() {
	return null;
    }
    
    public void editXML(File file,String data) {
	try {
	    FileUtils.writeStringToFile(new File(dataDirectory.getAbsolutePath()+"/device.xml"), data);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    public void generateMibBase() {
	
    }
    
    public void saveXML(List<?> listDevice) {
	
    }
    
    public void saveMibBase(LoadMibs loader) {
	
    }
    
   /* public List<?> getXML() {
	return null;
    }
    */
    
    public String getXML() {
	 InputStream in;
	 String xml=null;
	 try {
	    in=new FileInputStream(dataDirectory.getAbsolutePath()+"/device.xml");
	    xml=IOUtils.toString( in );
	    System.out.println(""+xml);
	    IOUtils.closeQuietly(in);
	    
	} catch (Exception e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	
	//String xml;
	//XStream xstream = new XStream(new DomDriver()); 
	///xstream.fromXML(dataDirectory.getAbsolutePath()+"/device.xml");
	//Person newJoe = (Person)xstream.from
	return xml;
    }
    
   
}
