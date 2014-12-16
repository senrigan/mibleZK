package com.gdc.nms.web.mibquery;

import java.util.ArrayList;

class TestC extends TestAbs{

    @Override
    public TestAbs getOID() {
	// TODO Auto-generated method stub
	return this;
    }
    public static void main(String [] args) {
	Testb b=new Testb();
	ArrayList<TestAbs> oid=new ArrayList<TestAbs>();
	oid.add(b);
	b.setOID(oid);
	System.out.println(b.getOID());
    }
   

   
    
}