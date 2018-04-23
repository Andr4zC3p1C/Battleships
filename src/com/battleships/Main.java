package com.battleships;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JPanel;

public class Main implements Runnable{
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static boolean Running = true;
	
	
	private Window win;
	private Canvas canvas;
	private Game game;
	private Thread thread;
	
	public Main(){
		canvas = new Canvas();
		canvas.setSize(new Dimension(512,HEIGHT));
		Input in = new Input();
		canvas.addMouseListener(in);
		canvas.addMouseMotionListener(in);
		
		game = new Game();
		
		JPanel panel = new JPanel();
		panel.add(canvas);
		panel.add(game);
		win = new Window("Battleships", panel);
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		
		long timer = System.nanoTime();
		long timeToSkip = 1000000000/60;
		
		while(Running){	
			if(System.nanoTime()-timer >= timeToSkip){
				timer = System.nanoTime();
				update();
				render();
			}
		}
	}
	
	private void update(){
		game.update();
	}
	
	private void render(){
		
		BufferStrategy bs = canvas.getBufferStrategy();
		
		if(bs == null){
			canvas.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, 600, HEIGHT);

		game.render(g);
		
		g.dispose();
		bs.show();
		
	}
	
	public static void addComponents(Component c){
		
		
	}
	
	public static void main(String[] args){
		
		new Main();
	}
}
