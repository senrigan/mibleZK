package com.gdc.nms.web.mibquery;

import java.io.File;
import java.util.ArrayList;

public class ControllerManager {
    private ArrayList<DeviceControllerManager> deviceList;
    
    protected ArrayList<DeviceControllerManager> getDevices() {
        return this.deviceList;
    }

    protected void setDevices(ArrayList<DeviceControllerManager> deviceList) {
        this.deviceList = deviceList;
    }

    public void editDevice(File device) {
	
    }
    
    public void exporDevice() {
	
    }
    
    public void newDevice() {
	
    }
}
