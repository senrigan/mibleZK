package com.gdc.nms.web.mibquery;

import java.util.Comparator;

public class nameComparator implements Comparator<MibInfo>{
	@Override
	public int compare(MibInfo mib,MibInfo mib2){
		return mib.getName().compareTo(mib2.getName());
	}

}
