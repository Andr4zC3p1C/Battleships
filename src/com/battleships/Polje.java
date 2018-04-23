package com.battleships;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Polje {
	public static final int PRAZNO = 0;
	public static final int LADJA = 1;
	public static final int ZADETEK = 2;
	public static final int FAIL = 3;
	
	public static final int NUM_SHOTS = 64;
	public static final int TILE_SIZE = 32;
	public static final int GRID_SIZE = 16;
	public static final int NUM_SHIPS = 8;
	
	
	private int stST;
	private int tocke;
	private Random rand;
	private boolean updating;
	private boolean gameOver;
	private boolean victory;
	
	private int polje[][];
	private BufferedImage[] imgs;
	
	public Polje(){
		
		imgs = new BufferedImage[3];
		try {
			imgs[0] = ImageIO.read(getClass().getResourceAsStream("/sea.png"));
			imgs[1] = ImageIO.read(getClass().getResourceAsStream("/explosion.png"));
			imgs[2] = ImageIO.read(getClass().getResourceAsStream("/splash.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		init();
	}
	public void init(){
		stST = NUM_SHOTS;
		gameOver = false;
		victory = false;
		updating = true;
		tocke = 0;
		rand = new Random();
		
		polje = new int[GRID_SIZE][GRID_SIZE];
		for(int i = 0; i<polje.length;i++){
			for(int j = 0;j<polje[0].length;j++){
				polje[i][j] = 0;
			}
		}
		
		ladjica();
	}
	
	public void render(Graphics g){
		
		for(int y = 0;y<GRID_SIZE;y++){
			for(int x = 0;x<GRID_SIZE;x++){
				g.drawImage(imgs[0], x*TILE_SIZE, y*TILE_SIZE, null);
				
				switch(polje[y][x]){
					
				case ZADETEK: 
					g.drawImage(imgs[1], x*TILE_SIZE, y*TILE_SIZE, null);
					break;
				case FAIL: 
					g.drawImage(imgs[2], x*TILE_SIZE, y*TILE_SIZE, null);
					break;
				}
				
			}
			
		}
		
		if(!gameOver){
			
			int MouseX = Input.mouseX;
			int MouseY = Input.mouseY;
			if(MouseX < 512 && MouseY < 512){
				
				g.setColor(new Color(0,0,0,100));
				g.fillRect((MouseX/TILE_SIZE)*TILE_SIZE, (MouseY/TILE_SIZE)*TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
			
		}else{
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
			g.setColor(Color.red);
			
			if(victory){
				
				g.drawString("VICTORY!", 120, 250);
				
			}else{
				
				g.drawString("GAME OVER!", 120, 250);
			}
		}
		
		g.setFont(new Font(Font.SANS_SERIF, 0, 20));
		g.setColor(Color.black);
		g.drawString("Shots left: "+stST, 20, 550);
		g.drawString("Score: "+tocke, 20, 580);
		
	}
	
	public void update(){	
		if(!updating)
			return;
		
		if(!gameOver){
			
			if(Input.buttonClicked(MouseEvent.BUTTON1)){
				int MouseX = Input.mouseX;
				int MouseY = Input.mouseY;
				
				MouseX = (MouseX/TILE_SIZE);
				MouseY = (MouseY/TILE_SIZE);
				
				if(MouseX < 0 || MouseX >= GRID_SIZE || MouseY < 0 || MouseY >= GRID_SIZE)
					return;
				
				switch(polje[MouseY][MouseX]){
					
				case PRAZNO: 
					polje[MouseY][MouseX] = FAIL;
					stST--;
					break;
					
				case LADJA: 
					polje[MouseY][MouseX] = ZADETEK;
					stST--;
					tocke += 10;
					break;
				}
				
				
			}
			
			if(stST == 0){
				gameOver = true;
			}
			
			if(tocke == NUM_SHIPS * 40){
				gameOver = true;
				victory = true;
			}	
		}else{
			Game.getScore().dodaj(tocke);
			updating = false;
		}
	}
	
	public void ladjica(){
		int counter = 0;
		while(counter < NUM_SHIPS - 1){
			int x = rand.nextInt((GRID_SIZE-1) * 100) / 100;
			int y = rand.nextInt((GRID_SIZE-1) * 100) / 100;
			int dir = rand.nextInt(300) / 100;
			
			if(ustreza(x, y, dir)){
				polje[y][x] = 1;

				switch(dir)
				{
				case 0:
					for(int j=1; j < 4; j++)
						polje[y][x + j] = LADJA;
					
					break;
					
				case 1:
					for(int j=1; j < 4; j++)
						polje[y][x - j] = LADJA;
					
					break;
					
				case 2:
					for(int j=1; j < 4; j++)
						polje[y + j][x] = LADJA;
					
					break;
					
				case 3:
					for(int j=1; j < 4; j++)
						polje[y - j][x] = LADJA;
					
					break;
				}
				
				counter++;
				continue;
			}
			
		}
		
	}
	
	private boolean ustreza(int x, int y, int direction){
		switch(direction){
		case 0:	// right
			for(int i=0; i < 4; i++){
				if(!(x + i < GRID_SIZE && x + i >= 0 && y < GRID_SIZE && y >= 0)){
					return false;
				}
				
				if(polje[y][x+i] == LADJA)
					return false;
			}
			
			break;
			
		case 1: // left
			for(int i=0; i < 4; i++){
				if(!(x - i < GRID_SIZE && x - i >= 0 && y < GRID_SIZE && y >= 0)){
					return false;
				}
				
				if(polje[y][x-i] == LADJA)
					return false;
			}
			
			break;
			
		case 2: // down
			for(int i=0; i < 4; i++){
				if(!(x < GRID_SIZE && x >= 0 && y + i < GRID_SIZE && y + i >= 0)){
					return false;
				}
				
				if(polje[y + i][x] == LADJA)
					return false;
			}
			
			break;
			
		case 3: // up
			for(int i=0; i < 4; i++){
				if(!(x < GRID_SIZE && x >= 0 && y - i < GRID_SIZE && y - i  >= 0)){
					return false;
				}
				
				if(polje[y - i][x] == LADJA)
					return false;
			}
			
			break;
		}
		
		return true;
	}
}
