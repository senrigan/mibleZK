package com.gdc.nms.web.mibquery.framework;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import jwf.WizardPanel;

public class PanelWiz extends WizardPanel{

    @Override
    public void display() {
	// TODO Auto-generated method stub

	

	       Border etched = (Border) BorderFactory.createEtchedBorder();


	        String[] items = {"A", "B", "C", "D"};
	        JList list = new JList(items);

	        JTextArea text = new JTextArea(10, 40);

	        JScrollPane scrol = new JScrollPane(text);
	        JScrollPane scrol2 = new JScrollPane(list);

	        JPanel panel= new JPanel();
	        this.add(scrol2,BorderLayout.WEST);
	        this.add(scrol, BorderLayout.EAST);    
	        this.setBorder(etched);

	        this.setVisible(true);
    }

    @Override
    public boolean hasNext() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public boolean validateNext(List list) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public WizardPanel next() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean canFinish() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean validateFinish(List list) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void finish() {
	// TODO Auto-generated method stub
	
    }

}
