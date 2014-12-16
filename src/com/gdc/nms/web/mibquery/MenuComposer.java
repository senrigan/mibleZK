package com.gdc.nms.web.mibquery;

import java.util.Arrays;

import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;


public class MenuComposer extends SelectorComposer<Component>{

   
    private static final long serialVersionUID = -2938915709081840018L;
 
    @Wire("#menu")
    	private Div menu; 
    
    @Wire("#menuBar")
    	private Navbar menuBar;
    
    @Wire("#middle")
    	private Div middle; 
    
    @Wire("#container")
    	private Div container; 
    
    @Listen("onClick=#menuBar")
    	public void changeMenu() {
		String menuName=menuBar.getSelectedItem().getLabel();
		Clients.showNotification(menuName);
		changeContainerPage(menuName);
    	}
    
    public void changeContainerPage(String dirPage){
	try {
	    Include page;
	    Label text;
	    text=new Label();
	    text.setValue(dirPage);
	    page=new Include();
	    page.setSrc(dirPage);
		
		//Clients.showNotification(page.getSrc());
		//container.getChildren().clear();
		//container.appendChild(dirPage);
	    container.appendChild(text);
	    
	}catch(Exception ex) {
	    Clients.showNotification(Arrays.toString(ex.getStackTrace()));
	}
	
	
    } 

}
