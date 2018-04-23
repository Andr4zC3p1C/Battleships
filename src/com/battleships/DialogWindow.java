package com.battleships;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogWindow extends JDialog{

	private String title;
	private JPanel panel;
	
	private Color backColor = new Color(207,207,207);
	private Color frontColor = new Color(140,140,140);

	public DialogWindow(String title, int width, int height){
		super();
		
		this.title = title;
	
		setTitle(title);
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}
	
	public void setPanelLayout(LayoutManager panelLayout){
		panel.setLayout(panelLayout);
	}
	
	public void addComponent(Component c){
		add(c);
	}
	
	public void addComponentToPanel(Component c){
		panel.add(c);
	}
	
	public void addLabel(String text, int x, int y, int width, int height, int fontSize){
		JLabel label = new JLabel();
		label.setText(text);
		label.setBounds(x, y, width, height);
		label.setBackground(backColor);
		label.setOpaque(true);
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
		
		panel.add(label);
	}
	
	public void addButton(String name, ActionListener al){
		JButton button =  new JButton(name);
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setBackground(backColor);
		button.setForeground(frontColor);
		button.setFocusPainted(false);
		button.addActionListener(al);
		
		panel.add(button);
	}
	
	public void pack(){
		super.add(panel);
		super.pack();
		setLocationRelativeTo(null);
	}
	
	public void setDialogVisible(boolean vi){
		super.setVisible(vi);
	}
	
}
