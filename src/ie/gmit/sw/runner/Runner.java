package ie.gmit.sw.runner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ie.gmit.sw.maze.Maze;
import ie.gmit.sw.maze.MazeView;
import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Node.NodePassage;

public class Runner implements KeyListener{
	
	private static final int MAZE_DIMENSION = 60;
	private Node[][] model;
	private MazeView view;
	private int currentRow;
	private int currentCol;
	
	public Runner() throws Exception{
		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);//60 X 60 MAZE
		model = m.getMaze();
    	view = new MazeView(model);
    	
    	placePlayer();
    	placeEnemy();
    	
    	Dimension d = new Dimension(MazeView.DEFAULT_VIEW_SIZE, MazeView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("Mario Maze");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
	}
	
	private void placePlayer(){   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model[currentRow][currentCol] = new Node();
    	model[currentRow][currentCol].setPassage(NodePassage.PLAYER);
    	updateView(); 		
	}
	
	private void placeEnemy(){
		for(int i = 0; i < 10; i++){
			int row = (int) (MAZE_DIMENSION * Math.random());
			int col = (int) (MAZE_DIMENSION * Math.random());
			model[currentRow][currentCol] = new Node();
			model[row][col].setPassage(NodePassage.ENEMY);
			Thread t = new Thread(){
				@Override
				public void run() {
					super.run();
				}
			};
			t.start();
		}
	}
	
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}
	
	int moveCount = 0;
	int sideCountRight = 3;
	int sideCountLeft = 5;
	int swordMoveCount = 7;

	//TODO: Slow down running
    public void keyPressed(KeyEvent e) {
    	//player up
    	if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)){
        		if(MazeView.hasSword){
    				if(swordMoveCount == 7){
        				MazeView.direction = swordMoveCount;
            			currentRow--;
        				swordMoveCount++;
        			}else{
        				MazeView.direction = 8;
            			currentRow--;
        				swordMoveCount--;
        			}
        		}else{
        			if(moveCount <= 2){
        				MazeView.direction = moveCount;
            			currentRow--;
        				moveCount++;
        			}else{
        				moveCount = 0;
        				MazeView.direction = moveCount;
            			currentRow--;
        				moveCount++;
        			}
        		}
        		updateView();
        	}
        }
    	//player down
    	else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)){
        		if(MazeView.hasSword){
        			if(swordMoveCount == 7){
        				MazeView.direction = swordMoveCount;
        				currentRow++;
        				swordMoveCount++;
        			}else{
        				MazeView.direction = 8;
        				currentRow++;
        				swordMoveCount--;
        			}
        		}else {
	    			if(moveCount <= 2){
	    				MazeView.direction = moveCount;
	    				currentRow++;
	    				moveCount++;
	    			}else{
	    				moveCount = 0;
	    				MazeView.direction = moveCount;
	    				currentRow++;
	    				moveCount++;
	    			}
        		}
        		updateView();
        	}
        }
    	//player right
    	else if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)){
        		if(MazeView.hasSword){
        			if(swordMoveCount == 9){
        				MazeView.direction = swordMoveCount;
        				currentCol++;
        				swordMoveCount++;
        			}else{
        				MazeView.direction = 10;
        				currentCol++;
        				swordMoveCount--;
        			}
        		}else{
	        		if(sideCountRight == 3){
	    				MazeView.direction = sideCountRight;
	    				currentCol++;
	    				sideCountRight++;
	    			}else if(sideCountRight == 4){
	    				MazeView.direction = sideCountRight;
	    				currentCol++;
	    				sideCountRight--;
	    			}
        		}
        		updateView();
        	}
        }
    	//player left
    	else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)){
        		if(MazeView.hasSword){
        			if(swordMoveCount == 11){
        				MazeView.direction = swordMoveCount;
        				currentCol--;
        				swordMoveCount++;
        			}else{
        				MazeView.direction = 12;
        				currentCol--;
        				swordMoveCount--;
        			}
        		}else{
		    		if(sideCountLeft == 5){
						MazeView.direction = 5;
						currentCol--;
						sideCountLeft = 6;
					}else if(sideCountLeft == 6){
						MazeView.direction = 6;
						currentCol--;
						sideCountLeft = 5;
					}
        		}
        		updateView();
        	}
        }
    	//toggle view
    	else if (e.getKeyCode() == KeyEvent.VK_Z){
        	view.toggleZoom();
        }else{
        	return;
        }
          
    }
    
    public void keyReleased(KeyEvent e) {//set all images to idle
    	if(MazeView.hasSword)
    		MazeView.direction = 7;
    	else
    		MazeView.direction = 2;
    }
    
	public void keyTyped(KeyEvent e) {} //Ignore

	private boolean isValidMove(int r, int c){
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getPassageType() == ' '){
			model[currentRow][currentCol].setPassage(NodePassage.NONE);
			model[r][c].setPassage(NodePassage.PLAYER);
			return true;
		}else{
			if(model[r][c].getPassageType() == 'W' && !MazeView.hasSword){
				model[r][c].setPassage(NodePassage.WALL);
				MazeView.hasSword = true;
				MazeView.direction = 7;
				view.repaint();
			}
			return false; //Can't move
		}
	}
	
	public static void main(String[] args) throws Exception{
		new Runner();
	}
}