package com.gdc.nms.web.mibquery;

import java.util.Iterator;

import net.percederberg.mibble.MibLoaderLog;
import net.percederberg.mibble.MibLoaderLog.LogEntry;

public class MibInfo {
	private String name;
	private String path;
	private MibLoaderLog log;
	
	public MibInfo(String name,String path,MibLoaderLog log){
		this.name=name;
		this.path=path;
		this.log=log;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setPath(String path){
		this.path=path;
	}
	
	public void setLog(MibLoaderLog log){
		this.log=log;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPath(){
		return this.path;
	}
	
	public MibLoaderLog getLog(){
		return this.log;
	}
	
	public String logToString(){
		String 		logString="";
		Iterator<?> 	logs;
		logs=log.entries();
		
		while(logs.hasNext())
		{
			LogEntry logx=(LogEntry) logs.next();
			logString+=logx;
		}
		
		return logString;
	}
	
	
}
