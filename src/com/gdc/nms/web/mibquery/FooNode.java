package com.gdc.nms.web.mibquery;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.percederberg.mibble.browser.MibNode;
import net.percederberg.mibble.value.ObjectIdentifierValue;

public class FooNode {
	private FooNode _parent;
	private List<FooNode> _children;
	private int _index;
	private String _label = "";
	private MibNode value;
	public FooNode (FooNode parent, int index, String label) {
		_parent = parent;
		_index = index;
		_label = label;
	}
	public void setParent (FooNode parent) {
		_parent = parent;
	}
	public FooNode getParent () {
		return _parent;
	}

	public void appendChild (FooNode child) {
		if (_children == null)
			_children = new ArrayList();
		_children.add(child);
	}
	public List<FooNode> getChildren () {
		if (_children == null)
			return Collections.EMPTY_LIST;
		return _children;
	}
	public boolean isParent(){
		if(_children==null)
			return false;
		return true;
	}
	public void setIndex (int index) {
		_index = index;
	}
	public void setLabel (String label) {
		_label = label;
	}
	public int getIndex () {
		return _index;
	}
	public String getLabel () {
		return _label;
	}
	public String toString () {
		return getLabel();
	}
	
	public void setMibNode(MibNode value){
		this.value=value;
	}
	public MibNode getMibNode(){
		return this.value;
	}
	
	public ObjectIdentifierValue getValue(){
		return value.getValue();
	}
	
	public Object getChild(Object parent, int index) {
        return ((FooNode)parent).getChildren().get(index);
    }
    public int getChildCount(Object parent) {
        return ((FooNode)parent).getChildren().size(); //each node has 5 children
    }
    public int getIndexOfChild(Object parent, Object child) {
    	List<FooNode> children = ((FooNode)parent).getChildren();
    	for (int i = 0; i < children.size(); i++) {
    		if (children.get(i).equals(children))
    			return i;
    	}
        return -1;
    }
}
