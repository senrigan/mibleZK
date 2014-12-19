package com.gdc.nms.web.mibquery;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Radiogroup;

public class DeviceOptionComposer extends SelectorComposer<Component> {
	@Wire("#optionDevice")
	Radiogroup optionDevice;
	
	@Listen("onCheck=#optionDevice")
	public void onChangeOption(){
		String name;
		name=optionDevice.getSelectedItem().getLabel();
		Clients.showNotification("item selected"+name,true);
	}

}
