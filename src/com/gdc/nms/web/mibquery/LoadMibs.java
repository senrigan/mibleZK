package com.gdc.nms.web.mibquery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibLoaderLog;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.snmp.SnmpObjectType;
import net.percederberg.mibble.value.ObjectIdentifierValue;

public class LoadMibs {
	private static LoadMibs instance = null;
	private static HashMap<String, MibValueSymbol> nodesTable;
	private MibLoader loader;
	private String dirPath = "/home/gilberto/Documentos/old data/docuemntos/datamib/";
	private ArrayList<MibInfo> loadedMib;
	private ArrayList<MibInfo> noLoadedMib;

	private LoadMibs() {
		if (instance == null) {
			loadedMib = new ArrayList<MibInfo>();
			noLoadedMib = new ArrayList<MibInfo>();
			loader = new MibLoader();
			nodesTable = new HashMap<String, MibValueSymbol>();
			addResource(loader);
		}

	}

	public static LoadMibs getInstance() {
		if (instance == null) {
			// synchronized (LoadMibs.class) {
			instance = new LoadMibs();
			// }
		}
		return instance;
	}

	public HashMap<String, ObjectIdentifierValue> loadMibs() {

		return getOID(loader);

	}
	
	private void addResource(MibLoader loader){
		File directory=new File(dirPath);
		File ietf=new File(dirPath+"/ietf");
		File iana=new File(dirPath+"/iana");
		File cisco=new File(dirPath+"/cisco");
		File _3com=new File(dirPath+"/3com");
		File windows=new File(dirPath+"/windows");
		File hauwei=new File(dirPath+"/hauwei");
		loader.addResourceDir(dirPath+"/ietf");
		loader.addResourceDir(dirPath+"/iana");
		loader.addResourceDir(dirPath);
		loader.addResourceDir(dirPath+"/cisco");
		loader.addDir(new File(dirPath+"/ietf"));
		loader.addDir(new File(dirPath+"/iana"));
		loader.addDir(new File(dirPath));
		loader.addDir(new File(dirPath+"/cisco"));
		loadResource(ietf,loader);
		loadResource(iana,loader);
		loadDirectory(cisco,loader);
		loadDirectory(_3com,loader);
		loadDirectory(windows,loader);
		loadDirectory(hauwei,loader);
		loadResource(directory,loader);
		
	}

	private void loadDirectory(File resource, MibLoader loader) {
		File[] listFile;
		MibInfo mibInfo;
		if (resource.isDirectory()) {
			listFile = resource.listFiles();
			for (int i = 0; i < listFile.length; i++) {

				mibInfo = null;
				try {
					if (listFile[i].isFile()) {
						loader.load(listFile[i]);
					} else if (listFile[i].isDirectory()) {
						loader.addResourceDir(listFile[i].getAbsolutePath());
						loader.addDir(listFile[i]);
						loadDirectory(listFile[i], loader);
					}

				} catch (IOException e) {
					mibInfo = new MibInfo(
							"IOException" + listFile[i].getName(),
							listFile[i].getAbsolutePath(), null);
					noLoadedMib.add(mibInfo);

				} catch (MibLoaderException e) {

					MibLoaderLog log = e.getLog();
					mibInfo = new MibInfo(listFile[i].getName(),
							listFile[i].getAbsolutePath(), log);
					noLoadedMib.add(mibInfo);
				}
				if (!(noLoadedMib.contains(mibInfo))) {
					mibInfo = new MibInfo(listFile[i].getName(),
							listFile[i].getAbsolutePath(), null);
					loadedMib.add(mibInfo);
				}

			}
		}
	}

	private void loadResource(File resource, MibLoader loader) {
		File[] listFile;
		MibInfo mibInfo;
		if (resource.isDirectory()) {
			listFile = resource.listFiles();
			for (int i = 0; i < listFile.length; i++) {
				mibInfo = null;
				try {
					if (listFile[i].isFile()) {
						loader.load(listFile[i]);

					}

				} catch (IOException e) {
					mibInfo = new MibInfo(
							"IOException" + listFile[i].getName(),
							listFile[i].getAbsolutePath(), null);
					noLoadedMib.add(mibInfo);

				} catch (MibLoaderException e) {
					MibLoaderLog log = e.getLog();
					mibInfo = new MibInfo(listFile[i].getName(),
							listFile[i].getAbsolutePath(), log);
					noLoadedMib.add(mibInfo);
				}
				if (!(noLoadedMib.contains("" + listFile[i].getName()))) {
					mibInfo = new MibInfo(listFile[i].getName(),
							listFile[i].getAbsolutePath(), null);
					loadedMib.add(mibInfo);
				}

			}
		}

	}

	public MibLoader getLoader() {
		return this.loader;
	}

	public ArrayList<MibInfo> getMibLoaded() {
		return loadedMib;
	}

	public ArrayList<MibInfo> getNotMibLoaded() {
		return noLoadedMib;
	}

	public void setLoader(MibLoader loader) {
		this.loader = loader;

	}

	public HashMap<String, MibValueSymbol> getOIDTable() {
		return nodesTable;
	}

	private static HashMap<String, ObjectIdentifierValue> getOID(
			MibLoader loader) {
		Iterator<?> iter;
		Mib[] mibs;
		MibSymbol symbol;
		HashMap<String, ObjectIdentifierValue> nodes = new HashMap<String, ObjectIdentifierValue>();
		mibs = loader.getAllMibs();
		for (Mib aux : mibs) {
			iter = aux.getAllSymbols().iterator();
			System.out.println("mib: " + aux.getName());
			while (iter.hasNext()) {
				MibValue value;
				ObjectIdentifierValue oid;
				MibValueSymbol oidTable;
				symbol = (MibSymbol) iter.next();
				if (symbol instanceof MibValueSymbol) {
					value = ((MibValueSymbol) symbol).getValue();
					oidTable = (MibValueSymbol) symbol;
					if (value instanceof ObjectIdentifierValue) {
						String oidString;
						oid = (ObjectIdentifierValue) value;
						oidString = "" + oid;
						if ((!nodes.containsKey(oidString))) {
							// System.out.println("oid"+oidString);
							if (oidTable.isTable()) {
								nodesTable.put(oidString, oidTable);
								// System.out.println("table :"+oidString);
							}
							nodes.put(oidString, oid);

						}

					}
				}
			}
		}
		return nodes;
	}
	
	public static void main(String [] args) {
	    LoadMibs l=LoadMibs.getInstance();
	    MibLoader miblod=l.getLoader();
	    Mib mibs[]=miblod.getAllMibs();
	    MibSymbol symbol;
	    Iterator<?> iter;
	    int nummibns=0;
	    for(Mib aux:mibs) {
		nummibns++;
		iter = aux.getAllSymbols().iterator();
		while (iter.hasNext()) {
			MibValue value;
			ObjectIdentifierValue oid;
			MibValueSymbol oidTable;
			symbol = (MibSymbol) iter.next();
			if (symbol instanceof MibValueSymbol) {
				value = ((MibValueSymbol) symbol).getValue();
				oidTable = (MibValueSymbol) symbol;
				MibValueSymbol symbol2=oidTable;
				 if (symbol != null && symbol2.getType() instanceof SnmpObjectType) {
				           SnmpObjectType snmp=(SnmpObjectType) symbol2.getType();
				           System.out.println("snmp nam"+snmp.getName());
				           System.out.println("snmp acces"+snmp.getAccess());
				           System.out.println("snmp augment"+snmp.getAugments());
				           System.out.println("snmp commet"+snmp.getComment());
				           System.out.println("snmp descriop"+snmp.getDescription());
				           System.out.println("snmp reference"+snmp.getReference());
				           System.out.println("snmp unit"+snmp.getUnits());
				           System.out.println("snmp defaultvalue"+snmp.getDefaultValue());
				           System.out.println("snmp index"+snmp.getIndex());
				           System.out.println("snmp status"+snmp.getStatus());
				           System.out.println("snmp syntaxis"+snmp.getSyntax());








				        } else {
				            System.out.println("algo paso");
				        }
				if (value instanceof ObjectIdentifierValue) {
					String oidString;
					oid = (ObjectIdentifierValue) value;
					oidString = "" + oid;
					//System.out.println("value oid"+oid.getValue());//valor final del oid
					System.out.println("type oid"+oid.getSymbol());

					System.out.println("table"+oid.getSymbol().isTable());
					System.out.println("escalar"+oid.getSymbol().isScalar());
					System.out.println("column"+oid.getSymbol().isTableColumn());
					System.out.println("row"+oid.getSymbol().isTableRow());

					try {
					    System.out.println("typeV oid"+oid.getSymbol().getType().getTag().getValue());
					    System.out.println("typeN oid"+oid.getSymbol().getType().getTag().getNext());

					}catch(Exception ex) {
					    System.out.println("error");

					}
					
					//System.out.println("type oid"+oid.getSymbol().getType().getTag().get);
					//System.out.println("symbol oid"+Arrays.toString(oid.getSymbol().getChildren()));
					//System.out.println("refer oid"+oid.getReferenceSymbol());
				}
			}
		}
	    }
	    System.out.println("total mibs"+ nummibns);
	}

}
