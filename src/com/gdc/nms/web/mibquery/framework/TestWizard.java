package com.gdc.nms.web.mibquery.framework;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import jwf.NullWizardPanel;
import jwf.Wizard;
import jwf.WizardAdapter;
import jwf.WizardContext;
import jwf.WizardListener;
import jwf.WizardPanel;

public class TestWizard {
    public static void main(String[] args) {
	NullWizardPanel pan=new NullWizardPanel();
	Wizard wiz=new Wizard();
	WizardAdapter adapter;
	WizardContext context;
	WizardListener listener;
	WizardPanel panel;
	System.out.println("empezando ventana");
	wiz.start(new PanelWiz());
	wiz.setVisible(true);
	wiz.setBackground(Color.BLUE);
	
	 JFrame frame = new JFrame();
	        frame.setSize(500,500);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
	        frame.add(wiz);
	        frame.setVisible(true);
	
    }
}
