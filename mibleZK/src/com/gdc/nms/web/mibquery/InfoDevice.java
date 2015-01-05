package com.gdc.nms.web.mibquery;

public class InfoDevice {
    private Company company;
    private Model   model;
    
    public InfoDevice(Company company,Model model) {
	this.company=company;
	this.model=model;
	
    }
    public Company getCompany() {
        return company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
    
    public Model getModel() {
        return model;
    }
    
    public void setModel(Model model) {
        this.model = model;
    }
    @Override
    public String toString() {
	return "InfoDevice [company=" + company + ", model=" + model + "]";
    }
    
}
