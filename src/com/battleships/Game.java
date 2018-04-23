package com.battleships;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Game extends JPanel{
	private JButton newGame , exit, clear;
	private static Score score;
	private Polje polje;
	
	public Game(){
		polje = new Polje();
		score = new Score();
		score.load();
		
		setSize(new Dimension(288, Main.HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		newGame = new JButton("NEW GAME");
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);

		exit = new JButton("EXIT");
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		clear = new JButton("CLEAR SCOREBOARD");
		clear.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		newGame.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				polje.init();
				
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		clear.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent arg0) {
				score.clearScore();
			}
		});

		add(newGame);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(clear);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(exit);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(score);
	}
	
	
	public void update(){
		polje.update();
	}
	
	public void render(Graphics g){	
		polje.render(g);	
	}
	
	public static Score getScore(){
		return score;
	}
}
