package com.battleships;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseListener, MouseMotionListener{

	public static int mouseX, mouseY;
	
	private static boolean[] buttons = new boolean[256];
	private static boolean[] pButtons = new boolean[256];
	
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}

	public void mouseClicked(MouseEvent e) {
		
		
	}

	public void mouseEntered(MouseEvent e) {
		
		
	}

	public void mouseExited(MouseEvent e) {
		
		
	}

	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
		
	}

	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
		pButtons[e.getButton()] = false;
	}
	
	public static boolean buttonPressed(int button){
		return buttons[button];
		
	}
	
	public static boolean buttonClicked(int button){
		boolean result = buttons[button] && !pButtons[button];
		if(result){
			pButtons[button] = true;
		}
		
		return result;
	}
	
	
}
