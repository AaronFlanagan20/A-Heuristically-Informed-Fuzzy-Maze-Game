package ie.gmit.sw.maze;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class MazeView extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private int cellspan;	
	private int cellpadding = 2;
	private Node[][] maze;
	private BufferedImage[] images;
	private int enemy_state = 5;
	private int player_state = 7;
	private int player_sword = 11;
	public static boolean hasSword = false;
	public static int direction = 0;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;
	
	public MazeView(Node[][] maze) throws Exception{
		this.maze = maze;
		init();
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}
	
	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze[currentRow].length - 1) - cellpadding){
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        cellspan = zoomOut ? maze.length : 5;         
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        
        for(int row = 0; row < cellspan; row++) {
        	for (int col = 0; col < cellspan; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		 
        		char ch;
        		
        		if (zoomOut){
        			ch = maze[row][col].getPassageType();
        			if(ch == 'E'){
        				g2.setColor(Color.BLUE);
        				g2.fillRect(x1, y1, size, size);
        			}
        			if (row == currentRow && col == currentCol){
        				g2.setColor(Color.YELLOW);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        		}else{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getPassageType();
        		}
        		
        		if (ch == 'X'){        			
        			imageIndex = 0;
        		}else if (ch == 'W'){
        			imageIndex = 1;
        		}else if (ch == '?'){
        			imageIndex = 2;
        		}else if (ch == 'B'){
        			imageIndex = 3;
        		}else if (ch == 'H'){
        			imageIndex = 4;
        		}else if (ch == 'P'){
        			imageIndex = player_state;       			
        		}else if (ch == 'E'){
        			imageIndex = enemy_state;
        		}else{
        			imageIndex = -1;
        		}
        		
        		if (imageIndex >= 0){
        			g2.drawImage(images[imageIndex], x1, y1, null);
        		}else{
        			g2.setColor(Color.LIGHT_GRAY);
        			g2.fillRect(x1, y1, size, size);
        		}      		
        	}
        }
	}
	
	public void toggleZoom(){
		zoomOut = !zoomOut;		
	}

	public void actionPerformed(ActionEvent e) {	
		switch(direction){
			case 0: player_state = 8; break;
			case 1: player_state = 9; break;
			case 2: player_state = 10; break;
			case 3: player_state = 11; break;
			case 4: player_state = 7; break;
//			case 5: player_state = 7; break;
//			case 6: player_state = 7; break;
//			case 7: player_state = 7; break;
		}
		
		if (enemy_state < 0 || enemy_state == 5){
			enemy_state = 6;
		}else{
			enemy_state = 5;
		}
		
		this.repaint();
	}
	
	private void init() throws Exception{
		images = new BufferedImage[30];
		images[0] = ImageIO.read(new java.io.File("resources/wall.png"));
		images[1] = ImageIO.read(new java.io.File("resources/sword.png"));		
		images[2] = ImageIO.read(new java.io.File("resources/prisoner.png"));
		images[3] = ImageIO.read(new java.io.File("resources/bomb.png"));
		images[5] = ImageIO.read(new java.io.File("resources/spider_down.png"));
		images[6] = ImageIO.read(new java.io.File("resources/spider_up.png"));
		images[7] = ImageIO.read(new java.io.File("resources/player_idle.png"));
		images[8] = ImageIO.read(new java.io.File("resources/player_up.png"));
		images[9] = ImageIO.read(new java.io.File("resources/player_down.png"));
		images[10] = ImageIO.read(new java.io.File("resources/player_right.png"));
		images[11] = ImageIO.read(new java.io.File("resources/player_left.png"));
		images[12] = ImageIO.read(new java.io.File("resources/player_sword.png"));
		images[13] = ImageIO.read(new java.io.File("resources/player_sword_up.png"));
		images[15] = ImageIO.read(new java.io.File("resources/player_sword_right.png"));
		images[14] = ImageIO.read(new java.io.File("resources/player_sword_left.png"));
	}
}