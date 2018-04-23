package com.battleships;

import java.awt.Component;

import javax.swing.JFrame;

public class Window {

	private JFrame frame;
	
	public Window(String title, Component... comps){
		frame = new JFrame(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int i=0; i < comps.length; i++){
			frame.add(comps[i]);
		}
		
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
}
