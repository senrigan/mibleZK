package com.gdc.nms.web.mibquery;

import com.thoughtworks.xstream.XStream;

 public class Person {
    private String firstname;
    private String lastname;
    private PhoneNumber phone;
    private PhoneNumber fax;
   
    public Person(String firstname,String lasname) {
	this.firstname=firstname;
	this.lastname=lasname;
    }
    
    public void setPhone(PhoneNumber phone) {
	this.phone=phone;
    }
    
    public void setFax(PhoneNumber phone) {
	this.fax=phone;
    }

   
    public String toString() {
	return "Person [firstname=" + firstname + ", lastname=" + lastname
		+ ", phone=" + phone + ", fax=" + fax + "]";
    }
    
   
  }

 class PhoneNumber {
    private int code;
    private String number;
    // ... constructors and methods
    public PhoneNumber(int code,String number) {
	this.code=code;
	this.number=number;
    }
    
   
  }
 