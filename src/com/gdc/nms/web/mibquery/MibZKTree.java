package com.gdc.nms.web.mibquery;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.zkoss.zk.ui.util.Clients;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.browser.MibNode;
import net.percederberg.mibble.value.ObjectIdentifierValue;
public class MibZKTree {
    private HashMap<MibValueSymbol, MibNode> nodes = new HashMap<MibValueSymbol, MibNode>();
    private FooNode rootFoo;
    private OID oid;
    FooNode nodeAux=new FooNode(null,0,"");
    
    FooNode createTree() throws IOException, MibLoaderException{
        Mib  prueba ;
        Mib [] mibs;
        MibLoader loader;
        
       
        return nodeAux;
    }
    
    
    public MibZKTree(){
        
    }
    
    
    
    
    
    public   Mib loadMib(File file) throws IOException, MibLoaderException {
            MibLoader  loader = new MibLoader();
            //loader.addDir(file.getParentFile());
            loader.addAllDirs(file);
            Mib  mibs;
            //mibs=loader.getAllMibs();
            mibs = loader.load(file);
            return mibs;
    }
    public FooNode addMib(Mib mib) {
        Iterator<?>   iter = mib.getAllSymbols().iterator();
        MibSymbol  symbol;
        FooNode    zkValueTree;
        
        rootFoo=new FooNode(null,0,"value");
        zkValueTree=new FooNode(rootFoo,0,"");
      
        while (iter.hasNext()) {
            symbol = (MibSymbol) iter.next();
            addSymbol(zkValueTree, symbol);
        }
        rootFoo.appendChild(zkValueTree);
        return rootFoo;
    }

    private void addSymbol (FooNode zkNode, MibSymbol symbol) {
        MibValue               value;
        ObjectIdentifierValue  oid;
      
        if (symbol instanceof MibValueSymbol) {
            value = ((MibValueSymbol) symbol).getValue();
            if (value instanceof ObjectIdentifierValue) {
                oid = (ObjectIdentifierValue) value;
                addToTree(zkNode,oid);
               
            
            }
        }
        
    }
    
    public  FooNode addSymbol (Map<OID, Variable> aliasMap) {
        int			position=0;
        String 					   m;
        LoadMibs load=LoadMibs.getInstance();
        FooNode					noIndex,entrace;
        ArrayList<String> noFound=new ArrayList<String>();
        HashMap<String,ObjectIdentifierValue> oidMibs;
        HashMap<String,MibValueSymbol> nodesTable ;
    	oidMibs=load.loadMibs();
    	nodesTable=load.getOIDTable();
    	rootFoo=new FooNode(null,0,"value");
    	noIndex=new FooNode(rootFoo,0,"no found");
    	entrace=new FooNode(rootFoo,0,"entrace");
		Iterator<?> it = aliasMap.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			m=""+e.getKey();
			if(m.endsWith(".0")){
				m=m.substring(0, m.length()-2);
			}
			
			if(oidMibs.containsKey(m)){
				addToTree(entrace,oidMibs.get(m));
				if(nodesTable.containsKey(m)){
					oid=new OID(m);
				}
					
					
			}else{
				
				if((searchPattern(m,oidMibs))==""){
					noFound.add(m);
					noIndex.appendChild(new FooNode (noIndex,position,""+e.getKey()));
					position++;
				}else{
					addToTree(entrace,oidMibs.get(searchPattern(m,oidMibs)));
				}
				
				
			}
				
			
		}
		rootFoo.appendChild(entrace);
		if(position!=0){
			rootFoo.appendChild(noIndex);
		}
		
        
		return rootFoo;
    
        
    }
    private String searchPattern(String value,HashMap<String,ObjectIdentifierValue> oidMibs){
    	boolean found=false;
    	String oidPat=""+oid;
    	do{
    		value=splitValue(value);
    		if(oidPat.equals(value)){
    			break;
    		}else if(oidMibs.containsKey(value)){
    			found=true;
    			return value;
    		}
    		
    	}while(!found);
    	return "";
    }
    private String splitValue(String value ){
    	value=value.substring(0,value.lastIndexOf("."));
    	return value;
    }
    public void setOIDPattern(OID oid){
    	this.oid=oid;
    }

    private FooNode addToTree(FooNode model, ObjectIdentifierValue oid) {
        FooNode  parent;
        FooNode  node;
        MibNode  nodeValue;
        String   name;
        int      count=0;
        // Add parent node to tree (if needed)
        if (hasParent(oid)) {
            System.out.println("padre");
            parent=addToTree(model, oid.getParent());
            System.out.println(parent.getChildren());
        } else {
        	System.out.println("no padre");
        	//parent =  rootFoo;
        	parent=model.getParent();
            System.out.println("parent "+parent.getLabel());
            
        }
    	for(int i=0;i<model.getChildCount(parent);i++){
    		
    		node=(FooNode)model.getChild(parent,i);
    		
            if(node.getValue().equals(oid)){
            	return node;                 
            }
        }
        name = oid.getName() + " (" + oid.getValue() + ")";
        nodeValue=new MibNode(name, oid);
        //System.out.println(name);
        node=new FooNode(parent,count,name);
       // System.out.println(node.getLabel());
        node.setMibNode(nodeValue);
        node.setLabel(name);
        //parent.add(node);
        parent.appendChild(node);
        System.out.println("parent**"+parent);
        nodes.put(oid.getSymbol(), nodeValue);
        return node;
    }
    
    private boolean hasParent(ObjectIdentifierValue oid) {
        ObjectIdentifierValue  parent = oid.getParent();

        return oid.getSymbol() != null
            && oid.getSymbol().getMib() != null
            && parent != null
            && parent.getSymbol() != null
            && parent.getSymbol().getMib() != null
            && parent.getSymbol().getMib().equals(oid.getSymbol().getMib());
    }

}
