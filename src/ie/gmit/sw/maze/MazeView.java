package ie.gmit.sw.maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ie.gmit.sw.maze.Node.NodePassage;

public class MazeView extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 600;	
	private Node[][] maze;
	
	public MazeView(Node[][] maze) {
		this.maze = maze;
		setBackground(Color.WHITE);
		setDoubleBuffered(true);
	}
		
	public void paint(Graphics g) {
        super.paintComponent(g);
       
        Graphics2D g2 = (Graphics2D)g;
        
        BufferedImage img;
        try {
            img = ImageIO.read(new File("imgs/brickwall.png"));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        final int size = DEFAULT_VIEW_SIZE/(maze.length - 7);//space in maze
        
        for(int row = 0; row < maze.length; row++) {
        	for (int col = 0; col < maze[row].length; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		int x2 = (col + 1) * size;
        		int y2 = (row + 1) * size;
        		
        		if (maze[row][col].isVisited() && !maze[row][col].isGoalNode()){
        			g2.setColor(Color.LIGHT_GRAY);
        			g2.fillRect(x1, y1, size, size);
        		}
        		
       			if (maze[row][col].isGoalNode()){
       				g2.setColor(Color.GREEN);
       				g2.fillRect(x1, y1, size, size);
       			}
       			
        		g2.setColor(Color.RED);
        		//g2.drawImage(img, x1, y1, x2, y2, x1, y1, x2, y2, null);
        		g2.drawLine(x1, y1, x2, y1); //N
        		g2.drawLine(x1, y2, x2, y2); //S
        		g2.drawLine(x2, y1, x2, y2); //E
        		g2.drawLine(x1, y1, x1, y2); //W
        		
  
        		if (maze[row][col].isVisited()){
        			g2.setColor(Color.LIGHT_GRAY);
        		}else{
        			g2.setColor(Color.WHITE);
        		}
        		
        		if (maze[row][col].getPassage() == NodePassage.North){
        			g2.drawLine(x1 + 1, y1, x2 - 1, y1); //N
        		}else{
        			g2.drawLine(x1, y1 + 1, x1, y2 -1); //W
        		}
        	}
        }
	}
}