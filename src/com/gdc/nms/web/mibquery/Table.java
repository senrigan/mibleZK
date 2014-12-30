package com.gdc.nms.web.mibquery;

import java.util.ArrayList;

public class Table {
    protected ArrayList<Column>columns;
    
    public ArrayList<Column>getColumns(){
	return this.columns;
    }
    
    public void setColumns(ArrayList<Column> columns) {
	this.columns=columns;
    }
}
