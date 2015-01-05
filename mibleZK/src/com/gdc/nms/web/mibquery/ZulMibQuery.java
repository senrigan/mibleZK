package com.gdc.nms.web.mibquery;



import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibValueSymbol;

//import org.apache.http.conn.util.InetAddressUtils;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Label;
import org.zkoss.zul.TreeModel;

/*import com.gdc.nms.model.Device;
import com.gdc.nms.server.DeviceCache;
import com.gdc.nms.server.ServerManager;
import com.gdc.nms.server.drivers.snmp.Driver;
import com.gdc.nms.server.drivers.snmp.DriverManager;
import com.gdc.nms.server.drivers.snmp.Snmp1Connector;
import com.gdc.nms.server.drivers.snmp.Snmp2Connector;
import com.gdc.nms.server.drivers.snmp.Snmp3Connector;
import com.gdc.nms.server.drivers.snmp.SnmpConnector;
import com.gdc.nms.server.drivers.snmp.SnmpConnectorException;

*/




public class ZulMibQuery extends SelectorComposer <Component>  {
	/*TreeModel model;
	MibZKTree dataTree ;
	private Device device;
	private DeviceCache deviceCache;
	@Wire("#listNoLoaded")
	private Listbox listNoLoaded;

	@Wire("#listLoaded")
	private Listbox listLoaded;
	
	@Wire("#ipAddress")
	private Textbox ipAddress;
	
	@Wire("#textOid")
	private Textbox textOid;
	
	@Wire
	private Button searchBtn;
	
	@Wire("#textDiv")
	private Div textDiv;
	
	@Wire("#treeMib")
	private Tree treeMib;
	
	@Wire
	private Button walkBtn;
	
	@Listen("onClick=#walkBtn")
public void snmpWalk(){
		
		if(device.isSnmpable()){
			SnmpConnector connector;
			OID oid=new OID(textOid.getText());
			Driver driver = DriverManager.getInstance().getDriver(device);
			switch (device.getSnmpVersion()) {
	            case V1:
			        if (driver.getSnmpConnector() == null
		                    || !(driver.getSnmpConnector().getClass() == Snmp1Connector.class)) {
			        	driver.setSnmpConnector(new Snmp1Connector());
		            }
	            	
	                break;
	            case V2:
	                if (driver.getSnmpConnector() == null
	                        || !(driver.getSnmpConnector().getClass() == Snmp2Connector.class)) {
	                    driver.setSnmpConnector(new Snmp2Connector());
	                }
	                break;
	            case V3:
			        if (driver.getSnmpConnector() == null
		                    || !(driver.getSnmpConnector().getClass() == Snmp3Connector.class)) {
			        	driver.setSnmpConnector(new Snmp3Connector());
		            }
	                break;
			case V0:
					if (driver.getSnmpConnector() == null
		               || !(driver.getSnmpConnector().getClass() == Snmp1Connector.class)) {
			        	driver.setSnmpConnector(new Snmp1Connector());
		            }
				break;
			default:
				break;
	        }
			connector=driver.getSnmpConnector();
			try {
				
				Map<OID, Variable> aliasMap;
				aliasMap = connector.walk(oid);
				treeMib.getChildren().clear();
				treeMib.clear();
				Clients.showNotification("alias Map: "+aliasMap.entrySet(), true);
				model = new FooModel(dataTree.addSymbol(aliasMap));
				treeMib.setModel(model);
				
					
					
			} catch (SnmpConnectorException e) {
				e.printStackTrace();
				Clients.showNotification("error snmp2", true);
			}
			
	
		}
		
	}
	/*static void main(String []args){
		TreeModel model;
		MibZKTree dataTree ;
		String ip="192.168.207.15";
		//Device device=null;
		DeviceCache deviceCache;
		Device device;
		deviceCache = ServerManager.getInstance().getDeviceCache();
		device=(isValidIp(ip)) ? deviceCache.byIp(ip) : deviceCache.byAlias(ip);
		if(device.isSnmpable()){
			SnmpConnector connector;
			OID oid=new OID("1.3.6.1.2.1.2.2");
			Driver driver = DriverManager.getInstance().getDriver(device);
			switch (device.getSnmpVersion()) {
	            case V1:
			        if (driver.getSnmpConnector() == null
		                    || !(driver.getSnmpConnector().getClass() == Snmp1Connector.class)) {
			        	driver.setSnmpConnector(new Snmp1Connector());
		            }
	            	
	                break;
	            case V2:
	                if (driver.getSnmpConnector() == null
	                        || !(driver.getSnmpConnector().getClass() == Snmp2Connector.class)) {
	                    driver.setSnmpConnector(new Snmp2Connector());
	                }
	                break;
	            case V3:
			        if (driver.getSnmpConnector() == null
		                    || !(driver.getSnmpConnector().getClass() == Snmp3Connector.class)) {
			        	driver.setSnmpConnector(new Snmp3Connector());
		            }
	                break;
			case V0:
					if (driver.getSnmpConnector() == null
		               || !(driver.getSnmpConnector().getClass() == Snmp1Connector.class)) {
			        	driver.setSnmpConnector(new Snmp1Connector());
		            }
				break;
			default:
				break;
	        }
			connector=driver.getSnmpConnector();
			try {
				
				Map<OID, Variable> aliasMap;
				aliasMap = connector.walk(oid);
				treeMib.getChildren().clear();
				treeMib.clear();
				Clients.showNotification("alias Map: "+aliasMap.entrySet(), true);
				model = new FooModel(dataTree.addSymbol(aliasMap));
				treeMib.setModel(model);
				
					
					
			} catch (SnmpConnectorException e) {
				e.printStackTrace();
				Clients.showNotification("error snmp2", true);
			}
			
	
		}
	}*/
	*/
	/*
	private void setListMib(){
		
		ArrayList<MibInfo>		mibLoad,mibNoLoad;
		Listitem 		mibListItem ;
		Listcell 		mibName ;
		String			value;
		LoadMibs load=LoadMibs.getInstance();
		listLoaded.getChildren().clear();
		listNoLoaded.getChildren().clear();
		mibLoad=load.getMibLoaded();
		mibNoLoad=load.getNotMibLoaded();
		Clients.showNotification("mibload"+mibLoad.size(), true);
		Clients.showNotification("mibnoload"+mibNoLoad.size(), true);
		Collections.sort(mibLoad,new nameComparator());
		Collections.sort(mibNoLoad,new nameComparator());
		for(int i=0;i<mibLoad.size();i++){
			mibListItem=new Listitem();
			value=(String) mibLoad.get(i).getName();
			mibName =new Listcell(value);
			mibListItem.appendChild(mibName);
			listLoaded.appendChild(mibListItem);
			
			
		}
		
		for(int i=0;i<mibNoLoad.size();i++){
			mibListItem=new Listitem();
			value=(String) mibNoLoad.get(i).getName();
			mibName =new Listcell(value);
			mibListItem.appendChild(mibName);
			listNoLoaded.appendChild(mibListItem);
			
			
		}
		
	}
	
	@Listen("onClick=#searchBtn")
	public void search(){
		String ip=ipAddress.getText();
		//Device device=null;
		device=(isValidIp(ip)) ? deviceCache.byIp(ip) : deviceCache.byAlias(ip);
		//device =ServerManager.getInstance().getDeviceCache().byIp(ip);
		if(device!=null){
			
			Clients.showNotification("si existe la ip");
		}
		else{
			Clients.showNotification("no existe ip");
		}
		
	}
	


	public void  doAfterCompose(Component c) throws Exception{
		super.doAfterCompose(c);
		deviceCache = ServerManager.getInstance().getDeviceCache();
		dataTree = new MibZKTree();
		treeMib.getChildren().clear();
		treeMib.clear();
		setListMib();
		//MibZKTree dataTree = new MibZKTree();
		//model = new FooModel(dataTree.createTree());
		//treeMib.setModel(model);
	
		//File directory=new File("/home/gil/Documentos/datamib");
		//Clients.showNotification("/home/gil/documentos/datamib"+directory.exists(),true);
		
		//Clients.showNotification("/datamib/"+directory.exists(),true);
		//Clients.showNotification("existe directorio"+directory.exists(),true);
		//Clients.showNotification("es carpeta"+directory.isDirectory(),true);
		
	}
	
	public void setTextDiv(String text){
		Label appendText=new Label();
		
		textDiv.getChildren().clear();
		appendText.setValue(text);
		textDiv.appendChild(appendText);
		
		
	}
	
	private static boolean isValidIp(String ip) {
		if(InetAddressUtils.isIPv4Address(ip) || InetAddressUtils.isIPv6Address(ip)){
			Clients.showNotification("is valid ip",true);
			return true;
			
		}
			
	    return false; 
	}
	
	@Listen("onSelect =#treeMib")
	public void displayInfo() {
		if(!treeMib.getSelectedItem().getValue().equals(""))
		{
			FooNode itemElement=(FooNode)treeMib.getSelectedItem().getValue();
			setTextDiv(itemElement.getMibNode().getDescription());

		}
	
    }
	

	

}
