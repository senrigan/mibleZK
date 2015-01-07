package com.gdc.nms.web.mibquery;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int age = 29;
    private String name = "mkyong";
	private List<String> messages = new ArrayList();
	public User(){
	    messages.add("msg 1");
		messages.add("msg 2");
		messages.add("msg 3");
	}
	

	//getter and setter methods

	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + ", " +
				"messages=" + messages + "]";
	}
}
