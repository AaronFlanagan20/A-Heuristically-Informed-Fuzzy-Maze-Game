package ie.gmit.sw.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import ie.gmit.sw.maze.Maze;
import ie.gmit.sw.maze.Node;
import ie.gmit.sw.runner.Runner;


/**
 * MazeView.java is JPanel that will be added to the main running JFrame
 * It uses the maze and checks all the values, 'X', 'W' etc and paints images according to them
 * 
 * It also allows for a zoomout feature that simulates looking over the maze.
 * 
 * @author Aaron - G00330035
 *
 *@see Maze
 *@see Runner
 */
public class MazeView extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private int cellspan;	
	private int cellpadding = 2;
	private Node[][] maze;
	private BufferedImage[] images;
	private int enemy_state = 5;
	private int player_state = 7;
	public static boolean hasSword = false;
	public static int direction = 2;
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
		timer = new Timer(175, this);//how long to wait before actionevent is triggered on panel
		timer.start();
	}
	
	//check where the player is and set the panel view accordingly
	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	//check where the player is and set the panel view accordingly
	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze[currentRow].length - 1) - cellpadding){
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	/**
	 * This method checks if the zoomout it enabled, if not it paints 5 squares/nodes on screen using cellspan
	 * and cellpadding is used to position the player within them nodes.
	 * 
	 * It uses a counter imageindex to determine what image to paint by referring the number to BufferedImage array below
	 * 
	 * 
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        g2.setFont(new Font("ITALIC", 1, 20));
		                              
        cellspan = zoomOut ? maze.length : 5; 
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        
        for(int row = 0; row < cellspan; row++) {
        	for (int col = 0; col < cellspan; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        				        		 
        		char ch = maze[row][col].getType();
        		
        		if (zoomOut){        			
	    			if(ch == 'E'){
	    				g2.setColor(Color.RED);
	    				g2.fillRect(x1, y1, size, size);
	    				continue;
	    			}
	    			
	    			if(ch == 'K'){
	    				g2.setColor(Color.GREEN);
	    				g2.fillRect(x1, y1, size, size);
	    				continue;
	    			}
	    			
	    			if(ch == '.'){
	    				g2.setColor(Color.CYAN);
	    				g2.fillRect(x1, y1, size, size);
	    				continue;
	    			}
	    			
	    			if(ch == 'W' || ch == 'S' || ch == 'T'){
	    				g2.setColor(Color.BLUE);
	    				g2.fillRect(x1, y1, size, size);
	    				continue;
	    			}
	    			       			
	    			if (row == currentRow && col == currentCol){
	    				g2.setColor(Color.YELLOW);
	    				g2.fillRect(x1, y1, size, size);
	    				continue;
	    			}
        		}else{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getType();
        		}
        		
        		int image = 23;
        		if(Runner.playerHealth == 100){
        			image = 23;
        		}else if(Runner.playerHealth == 75){
        			image = 24;
        		}else if(Runner.playerHealth == 50){
        			image = 25;
        		}else if(Runner.playerHealth == 25){
        			image = 26;
        		}
        		
        		if(!zoomOut){
        			g2.setColor(Color.RED);
        			g2.drawString("Health", 90, 20);
        			g2.drawImage(images[image], 22, 30, null);
        			
        			//STEPS ON SCREEN
            		int stepsLeft = 1999 - Runner.stepsLeft;
            		g2.drawString("Steps Left", 250, 20);
            		g2.drawString("" + stepsLeft, 270, 45);
        		}
        		
        		if (ch == 'X'){        			
        			imageIndex = 0;
        		}else if (ch == 'W'){
        			imageIndex = 1;
        		}else if (ch == '?'){
        			imageIndex = 2;
        		}else if (ch == 'B'){
        			imageIndex = 3;
        		}else if (ch == '.'){
            		imageIndex = 20;
        		}else if (ch == 'T'){
            		imageIndex = 21;
        		}else if (ch == 'S'){
            		imageIndex = 22;
        		}else if (ch == 'K'){
            		imageIndex = 28;
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

	/**
	 * The timer triggers events every 3.5 seconds to change the enemy_state and player_state, this simulates the movement
	 */
	public void actionPerformed(ActionEvent e) {	
		switch(direction){
			case 0: player_state = 8; break;//up
			case 1: player_state = 9; break;//down
			case 2: player_state = 7; break;//idle
			case 3: player_state = 10; break;//player right
			case 4: player_state = 11; break;//player right midrun
			case 5: player_state = 12; break;//player left
			case 6: player_state = 13; break;//player left midrun
			case 7: player_state = 14; break;//player sword
			case 8: player_state = 15; break;//player sword up
			case 9: player_state = 16; break;//player sword right
			case 10: player_state = 17; break;//player sword midrun right
			case 11: player_state = 18; break;//player sword left
			case 12: player_state = 19; break;//player sword midrun left
			case 13: player_state = 27; break;//player sword up midrun
		}
						
		if (enemy_state < 0 || enemy_state == 5){
			enemy_state = 6;
		}else{
			enemy_state = 5;
		}
		
		this.repaint();
	}
	
	/**
	 * Setup and store all images needed to paint on screen
	 * @throws Exception
	 */
	private void init() throws Exception{
		images = new BufferedImage[29];
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
		images[11] = ImageIO.read(new java.io.File("resources/player_midrun_right.png"));
		images[12] = ImageIO.read(new java.io.File("resources/player_left.png"));
		images[13] = ImageIO.read(new java.io.File("resources/player_midrun_left.png"));
		images[14] = ImageIO.read(new java.io.File("resources/player_sword.png"));
		images[15] = ImageIO.read(new java.io.File("resources/player_sword_up.png"));
		images[16] = ImageIO.read(new java.io.File("resources/player_sword_right.png"));
		images[17] = ImageIO.read(new java.io.File("resources/player_midrun_right_sword.png"));
		images[18] = ImageIO.read(new java.io.File("resources/player_sword_left.png"));
		images[19] = ImageIO.read(new java.io.File("resources/player_midrun_left_sword.png"));
		images[20] = ImageIO.read(new java.io.File("resources/stairs_exit.png"));
		images[21] = ImageIO.read(new java.io.File("resources/toothpick.png"));
		images[22] = ImageIO.read(new java.io.File("resources/spiderspray.png"));
		images[23] = ImageIO.read(new java.io.File("resources/health-bar100.png"));
		images[24] = ImageIO.read(new java.io.File("resources/health-bar75.png"));
		images[25] = ImageIO.read(new java.io.File("resources/health-bar50.png"));
		images[26] = ImageIO.read(new java.io.File("resources/health-bar25.png"));
		images[27] = ImageIO.read(new java.io.File("resources/player_sword_up_midrun.png"));
		images[28] = ImageIO.read(new java.io.File("resources/slinky-key.png"));
	}
}