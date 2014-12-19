package com.gdc.nms.web.mibquery;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

public class DeviceOptionComposer extends SelectorComposer<Component> {
	@Wire("#winModal")
	private Window winModal;
	
	@Wire("#midleContent")
	private Div midleContent;
	
	private int numPage=0;
    	@Wire("#nextButton")
	private Button nextButton;
	@Wire("#backButton")
	private Button backButton;
	@Wire("#cancelButton")
	private Button cancelButton;
	
    	@Wire("#optionDevice")
	Radiogroup optionDevice;
	
	@Listen("onCheck=#optionDevice")
	public void onChangeOption(){
		String name;
		name=optionDevice.getSelectedItem().getLabel();
		Clients.showNotification("item selected"+name,true);
	}
	
	@Listen("onClick=#nextButton")
	public void nextAction() {
	    numPage++;
	    //Clients.showNotification("next");
	    midleContent.getChildren().clear();
	    Clients.showNotification(""+optionDevice.getSelectedItem().getValue());
	    changePage();
	}
	
	@Listen("onClick=#backButton")
	public void backAction() {
	    numPage--;
	    Clients.showNotification("back");
	    
	}
	@Listen("onClick=#cancelButton")
	public void cancelAction() {
	    Clients.showNotification("Cancel");
	    winModal.detach();
	}
	
	private void changePage() {
	    Include page=new Include();
	    switch(numPage) {
	    
	    case 1:
		int a=optionDevice.getSelectedItem().getValue();
		if(a==0) {
		    page.setSrc("newDeviceManual.zul");
		}else if(a==1) {
		    page.setSrc("newDeviceByIP.zul");
		}
		Clients.showNotification(""+page.getSrc());
		midleContent.appendChild(page);
		break;
		
	    }
	}
	
	@Listen("onCancel=#winModal")
	public void onCancel() {
	    winModal.detach();
	}

}
