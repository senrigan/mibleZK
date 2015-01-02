package com.gdc.nms.web.mibquery;

public class Company {
   

    private int 	id;
    private String 	companyName;
    
    public Company(int id,String companyName) {
	this.id=id;
	this.companyName=companyName;
    }
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    @Override
    public String toString() {
	return "Company [id=" + id + ", companyName=" + companyName + "]";
    }
}
