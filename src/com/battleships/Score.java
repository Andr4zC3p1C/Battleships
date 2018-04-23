package com.battleships;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Score extends JPanel{
	
	public static final int SCORE_SIZE = 10;
	public static final String SCORE_FILE = "Battleship_topscores.hs";
	
	public static int[] score;
	private String[] names;
	private JLabel[] labels;
	private int m_score;
	
	public Score(){
		
		score = new int[SCORE_SIZE];
		names = new String[SCORE_SIZE];
		labels = new JLabel[SCORE_SIZE];
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel tsc = new JLabel("HIGH SCORES:");
		tsc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		tsc.setAlignmentX(CENTER_ALIGNMENT);
		add(tsc);
		
		for(int i = 0;i<SCORE_SIZE;i++){
			names[i] = ".....";
			labels[i] = new JLabel((i+1)+". "+names[i]+" = "+score[i]);
			labels[i].setAlignmentX(CENTER_ALIGNMENT);
			add(labels[i]);
		}
	}
	
	public void dodaj(int score){
		m_score = score;
		
		final DialogWindow win = new DialogWindow("Enter your name:", 200, 100);
		
		JTextField area = new JTextField();
		area.setSize(new Dimension(200,30));
		win.addComponentToPanel(area);
		win.addComponentToPanel(Box.createRigidArea(new Dimension(0, 20)));
		win.addButton("OK", new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String name = area.getText();
				if(name.length() > 15){
					name = name.substring(0, 14) + "...";
				}
				
				for(int k = 0; k<SCORE_SIZE; k++){
					if(score>=Score.score[k]){
						for(int g = SCORE_SIZE-2;g>=k;g--){
							Score.score[g+1]=Score.score[g];
							names[g+1]=names[g];
						}
						Score.score[k]=score;
						names[k]=name;
						break;
					}
				}
				
				refreshLabels();
				write();
				win.dispose();
			}
		});
		
		win.pack();
		win.setVisible(true);
	}
	
	public void load(){
		
		String userHome = System.getProperty("user.home").replace('\\', '/');
		File file = new File(userHome+"/" + SCORE_FILE);
		
		if(!file.exists())
			write();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			int j = 0;
			while((line = br.readLine())!=null){
				String []neki = line.split(",");
				names[j] = neki[0];
				score[j] = Integer.parseInt(neki[1]);		
				j++;
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		refreshLabels();
	}
	
	
	public void write(){
		
		try {
			String userHome = System.getProperty("user.home").replace('\\', '/');
			PrintWriter wrtr = new PrintWriter(userHome+"/" + SCORE_FILE);
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i<10;i++){
				
				sb.append(names[i]+","+score[i]+"\n");
				
			}
			
			wrtr.write(sb.toString());
			wrtr.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void refreshLabels(){
		
		for(int j = 0;j<SCORE_SIZE;j++){
			labels[j].setText((j+1)+". "+names[j]+" = "+this.score[j]);
		}
		
	}
	
	public void clearScore(){
		
		for(int i = 0;i<SCORE_SIZE;i++){
			names[i] = ".....";
			score[i] = 0;
		}
		
		refreshLabels();
		write();
	}
}
