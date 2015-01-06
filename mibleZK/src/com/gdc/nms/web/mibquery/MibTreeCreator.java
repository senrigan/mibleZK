package com.gdc.nms.web.mibquery;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.browser.MibNode;
import net.percederberg.mibble.value.ObjectIdentifierValue;
import org.zkoss.zul.Tree;

public class MibTreeCreator {
	private JTree mibTree=null;
	private HashMap nodes = new HashMap();
	
	void createTree() throws IOException, MibLoaderException{
		Mib prueba ;
		File archivo=new File("/home/gil/Documentos/A100-R1-MIB.my");
		prueba=loadMib(archivo);
		addMib(prueba);
	}
	
	public TreeModel getTree(){
		return mibTree.getModel();
	}
	public MibTreeCreator(){
		 int  mode = TreeSelectionModel.SINGLE_TREE_SELECTION;

	        mibTree = new MibTree();
	        mibTree.setToolTipText("");
	        mibTree.getSelectionModel().setSelectionMode(mode);
	        mibTree.setRootVisible(false);
	}
	
	
	
	public   Mib loadMib(File file) throws IOException, MibLoaderException {
		    MibLoader  loader = new MibLoader();
		    loader.addDir(file.getParentFile());
		    return loader.load(file);
	}
	
	public void addMib(Mib mib) {
        Iterator   iter = mib.getAllSymbols().iterator();
        MibSymbol  symbol;
        MibNode    root;
        MibNode    node;
        JTree      valueTree;

        // Create value sub tree
        node = new MibNode("VALUES", null);
        valueTree = new JTree(node);
        while (iter.hasNext()) {
            symbol = (MibSymbol) iter.next();
           // System.out.println("simbolo"+symbol);
            addSymbol(valueTree.getModel(), symbol);
        }

        // TODO: create TYPES sub tree

        // Add sub tree root to MIB tree
        root = (MibNode) mibTree.getModel().getRoot();
        node = new MibNode(mib.getName(), null);
        node.add((MibNode) valueTree.getModel().getRoot());
        root.add(node);
        System.out.println("root"+root.getChildAt(0).getChildAt(0).getChildAt(0));
    }
	
	private void addSymbol(TreeModel model, MibSymbol symbol) {
        MibValue               value;
        ObjectIdentifierValue  oid;

        if (symbol instanceof MibValueSymbol) {
            value = ((MibValueSymbol) symbol).getValue();
            if (value instanceof ObjectIdentifierValue) {
                oid = (ObjectIdentifierValue) value;
                //System.out.println("valor "+oid);
                addToTree(model, oid);
            }
        }
    }
	
	private MibNode addToTree(TreeModel model, ObjectIdentifierValue oid) {
        MibNode  parent;
        MibNode  node;
        String   name;

        // Add parent node to tree (if needed)
        if (hasParent(oid)) {
            parent = addToTree(model, oid.getParent());
        } else {
            parent = (MibNode) model.getRoot();
        }

        // Check if node already added
        for (int i = 0; i < model.getChildCount(parent); i++) {
            node = (MibNode) model.getChild(parent, i);
            if (node.getValue().equals(oid)) {
                return node;
            }
        }

        // Create new node
        name = oid.getName() + " (" + oid.getValue() + ")";
        node = new MibNode(name, oid);
        parent.add(node);
        nodes.put(oid.getSymbol(), node);
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
	  
	  public static void main(String  [] args) throws IOException, MibLoaderException{
		  MibTreeCreator tree=new MibTreeCreator();
		  tree.createTree();
		  Tree zktree=new Tree();
		  org.zkoss.zul.TreeModel mundo =(org.zkoss.zul.TreeModel) tree.getTree();
		  
		 // zktree.setModel((org.zkoss.zul.TreeModel<?>)tree.getTree());
		  TreeModel aux=tree.getTree();
		  System.out.println("arbol*"+aux.getRoot().toString());
		  zktree.setModel(mundo);
		  //zktree.setModel((org.zkoss.zul.TreeModel<?>) tree.getTree());
		  //System.out.println(zktree.getTreechildren());
	  }

}
