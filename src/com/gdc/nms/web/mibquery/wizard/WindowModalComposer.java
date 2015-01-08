package com.gdc.nms.web.mibquery.wizard;

import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

public class WindowModalComposer extends SelectorComposer<Component> {

	private boolean positionOutWindows=false;
	
	@Wire("#divContent")
   	private Div divContent;
	   
	@Wire("#windowModal")
		private Window windowModal;
	
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
	  
	  
	@Listen("onMouseOut=#windowModal")
	public void exitWindoModal() {
		this.positionOutWindows=true;
		Clients.showNotification("se salio"+this.positionOutWindows,true);
	
	}
}
