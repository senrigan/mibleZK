package com.gdc.nms.web.mibquery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;


public class MenuComposer extends SelectorComposer<Component>{

   
    private static final long serialVersionUID = -2938915709081840018L;
 
    private boolean positionOutWindows=false;
    @Wire("#divContent")
    	private Div divContent;
    @Wire("#windowModal")
    	private Window windowModal;
    
    @Wire("#menu")
    	private Div menu; 
    
    @Wire("#menuBar")
    	private Navbar menuBar;
    @Wire("#menuPoller")
    	private Nav menuPoller;
    
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
    @Listen("onOpen=#menuPoller")
    	public void pollerOption() {
		
		String menuName=menuPoller.getNavbar().getSelectedItem().getLabel();
		Clients.showNotification(menuName);
		
    	}
    @Listen("onClick=#menuPoller > navitem")
	public void pollerOption3() {
		
		String menuName=menuPoller.getNavbar().getSelectedItem().getLabel();
		//Clients.showNotification(menuName);
	}
    @Listen("onClick=Nav > Navitem")
	public void pollerOption2() {
		//Clients.showNotification("algo se dropeo",true);
		String menuName=menuPoller.getNavbar().getSelectedItem().getLabel();
		
		//Clients.showNotification(menuName);
		 Window window = (Window)Executions.createComponents(
		                "modal.zul", null, null);
		        window.doModal();
		        //window.doOverlapped();
		
		 List<Component> comp=window.getChildren();
		 String m="";
		 for(Component aux:comp) {
		     m=aux.getClass().getName();
		    // System.out.println(""+aux.getClass().getName());
		 }
		 Clients.showNotification(""+m+window.getRoot().getClass().getName(),true);
	
	}
    @Listen("onMouseOut=#windowModal")
    	public void exitWindoModal() {
		this.positionOutWindows=true;
		Clients.showNotification("se salio"+this.positionOutWindows,true);
		
    	}
    @Listen("onCancel=#windowModal")
    	public void escaModal() {
	Clients.showNotification("esk", true);
	windowModal.detach();
    }
    @Listen("onMouseOver=#windowModal")
	public void clickWindoModal() {
		this.positionOutWindows=false;
		Clients.showNotification("esta adentro"+this.positionOutWindows,true);
	}
    @Listen("onRightClick=#windowModal")
    	public void rightClickModal() {
		Clients.showNotification("click derecho", true);
    }
    
    @Listen("onClick=#divContent")
	public void chek() {
	    Clients.showNotification("mmm",true);
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
