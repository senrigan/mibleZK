package com.gdc.nms.web.mibquery;

import java.util.ArrayList;

public abstract class TestAbs {
    protected String acces;
    protected String name;
    protected ArrayList<TestAbs> oid;
    protected String typeOID;  
    
    public abstract TestAbs getOID();
}

class Testb extends TestAbs{

    @Override
    public TestAbs getOID() {
	// TODO Auto-generated method stub
	return this;
    }
    public void setOID(ArrayList<TestAbs> oids) {
	this.oid=oids;
    }
   

   
    
}


