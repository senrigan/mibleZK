package com.gdc.nms.web.mibquery;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;

public class ManualIPComposerStep3 {
	@Listen("onClick=#nextButton")
	public void nextAction() {
	    //numPage++;
	    Clients.showNotification("next");
	    //midleContent.getChildren().clear();
	    //Clients.showNotification(""+optionDevice.getChildren());
	    //Clients.showNotification(""+optionDevice.getSelectedItem().getValue());
	    //changePage();
	}
	
	@Listen("onClick=#backButton")
	public void backAction() {
	   // numPage--;
	    Clients.showNotification("back");
	    
	}
	@Listen("onClick=#cancelButton")
	public void cancelAction() {
	    Clients.showNotification("Cancel");
	    //winModal.detach();
	}
}
