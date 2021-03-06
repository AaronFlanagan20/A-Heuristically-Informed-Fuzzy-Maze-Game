package ie.gmit.sw.runner;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import ie.gmit.sw.fuzzylogic.Fighting;
import ie.gmit.sw.maze.Maze;
import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Node.NodeType;
import ie.gmit.sw.view.GameOver;
import ie.gmit.sw.view.MazeView;

/**
 * Runner.java is the main class. It creates a mazeView object, sets it's dimensions and adds it to the frame.
 * It places players and enemies on screen, performs collision detection and uses
 * keylisteners to determine where the user is moving.
 * 
 * The Fuzzy-Maze-Game.jar can be run on the command-line using the following command: java �cp ./Fuzzy-Maze-Game.jar ie.gmit.sw.runner.Runner
 * 
 * @author Aaron - G00330035
 * 
 * @see Maze
 * @see MazeView
 * @see Node
 */
public class Runner extends JFrame implements KeyListener{

	private static final long serialVersionUID = 1L;

	private static final int MAZE_DIMENSION = 60;
	
	private Node[][] model;
	private MazeView view;
	private int currentRow;
	private int currentCol;
	private Thread[] enemies;
	private Map<Node, Integer> enemiesHealth;
	private int weaponStrength = 0;
	public static int playerHealth = 100;
	public static int stepsLeft = 0;
	private boolean hasKey = false;
	
	/**
	 * Add in MazeView, create a Thread array for enemies to move and a list to store the enemy and their health
	 * 
	 * place players and enemies on screen and start up the frame
	 * 
	 * @throws Exception to handle A FileNotFoundException in MazeView
	 */
	public Runner() throws Exception{
		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);//60 X 60 MAZE
		model = m.getMaze();
    	view = new MazeView(model);

    	enemies = new Thread[10];//thread for each enemy
    	enemiesHealth = new HashMap<Node, Integer>();//strength value for each enemy
    	
    	placePlayer();
    	placeEnemy();
    	placeKey();
    		    	
    	Dimension d = new Dimension(MazeView.DEFAULT_VIEW_SIZE, MazeView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	setTitle("Mazogs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        getContentPane().setLayout(new FlowLayout());
        add(view);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        for(Thread t: enemies){
        	t.start();
        }
        
	}
	
	/**
	 * Place player randomly in maze
	 */
	private void placePlayer(){   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	if(model[currentRow][currentCol].getType() != 'X' && model[currentRow][currentCol].getType() == ' '){
    		model[currentRow][currentCol].setType(NodeType.PLAYER);
    	}else{
    		placePlayer();
    	}
    	updateView();
	}
	
	/**
	 * Place 10 enemies in random positions and create a thread for them
	 * 
	 */
	
	private void placeEnemy(){
		for(int i = 0; i < 10; i++){
			int row = (int) (MAZE_DIMENSION * Math.random());
			int col = (int) (MAZE_DIMENSION * Math.random());
			model[row][col] = new Node(row,col);
			if(model[row][col].getType() == ' '){
				model[row][col].setType(NodeType.ENEMY);
				Thread t = new Thread(new Runnable() {
					public void run() {
						while(true){
							int num = (int)(Math.random() * 10);
							if(num >= 5)
								randomWalk(model, model[row][col], view, true);
							else
								randomWalk(model, model[row][col], view, false);
						}
					}
				});
				enemies[i] = t;
			}else{
				System.out.println("going back");
				i--;
			}
				
		}
	}
	
	private void placeKey(){
		int row  = (int) (MAZE_DIMENSION * Math.random());
    	int col = (int) (MAZE_DIMENSION * Math.random());
    	
    	if(model[row][col].getType() == ' '){
    		model[row][col].setType(NodeType.KEY);
    	}else{
    		placeKey();
    	}
	}
	
	/**
	 * This method determines how the user moves independently through the maze and determines how the strong the enemy will be
	 * 
	 * @param maze
	 * @param node
	 * @param viewer
	 * @param lifo
	 */
	private void randomWalk(Node[][] maze, Node node, Component viewer, boolean lifo){
		
		int num = (int)(Math.random() * 10);
		
		switch(num){
			case 1: enemiesHealth.put(node, 1); break;
			case 2: enemiesHealth.put(node, 2); break;
			case 3: enemiesHealth.put(node, 3); break;
			case 4: enemiesHealth.put(node, 4); break;
			case 5: enemiesHealth.put(node, 5); break;
			case 6: enemiesHealth.put(node, 7); break;
			case 7: enemiesHealth.put(node, 5); break;
			case 8: enemiesHealth.put(node, 8); break;
			case 9: enemiesHealth.put(node, 5); break;
			case 10: enemiesHealth.put(node, 6); break;
	}
		
//		LinkedList<Node> queue = new LinkedList<Node>();
//		queue.add(node);
//			
//		while (!queue.isEmpty()){
//			
//			Node next = queue.poll();
//			
//			if(next != null){
//				next.setVisited(true);
//				
//				try{
//					Node[] children = next.children(maze);
//				 	for (int i = 0; i < children.length; i++) {
//				 		if (!children[i].isVisited()){
//				 			//children[i-1].setType(NodeType.NONE);
//				 			children[i].setType(NodeType.ENEMY);
//				 			if(lifo){
//				 				queue.addFirst(children[i]);
//				 			}else{
//				 				queue.addLast(children[i]);
//				 			}
//				 		}
//					}
//				}catch(ArrayIndexOutOfBoundsException e){
//					//When trying to walk through the mazes surrounding walls
//				}
//				
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		Iterator<Node> it = queue.descendingIterator();
//		
//		while(it.hasNext()){
//			Node next = queue.poll();
//			next.setType(NodeType.ENEMY);
//			
//			if(next != null){
//				next.setVisited(true);
//				
//				Node[] children = next.children(maze);
//			 	for (int i = 0; i < children.length; i++) {
//			 		if (!children[i].isVisited()){
//			 			if(lifo){
//			 				queue.addFirst(children[i]);
//			 			}else{
//			 				queue.addLast(children[i]);
//			 			}
//			 		}
//				}
//			}
//		}
		
		int r = node.getRow();
		int c = node.getCol();
		
		while(true){
			 for(int x=-1;x<=1;x++){
	        		try{
	        			Thread.sleep(200);
	        			if(maze[r+x][c].getType() == ' '){
	        				maze[r][c].setType(NodeType.NONE);
	        				r+=x;
	        			}if(maze[r][c+x].getType() == ' '){
	        				maze[r][c].setType(NodeType.NONE);
	        				c+=x;
	        			}if(maze[r-x][c].getType() == ' '){
	        				maze[r][c].setType(NodeType.NONE);
	        				r-=x;
	        			}if(maze[r][c-x].getType() == ' '){
	        				maze[r][c].setType(NodeType.NONE);
	        				c-=x;
	        			}
	        		}catch(Exception e){ // ignore ArrayIndexOutOfBounds
	        			continue;
	        		}
	        		
	        		
    				maze[r][c].setType(NodeType.ENEMY);
	        		
    				try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			 }
		}
	}
	
	/**
	 * Check where the user is and adjust accordingly
	 */
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}
	
	private int moveCount = 0;
	private int sideCountRight = 3;
	private int sideCountLeft = 5;
	private int swordMoveCount = 7;
	
	private long lastPress;

    public void keyPressed(KeyEvent e) {
    	try{
    		if(System.currentTimeMillis() - lastPress > 250) {
    			//player up
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W && currentRow > 0) {
					if(stepsLeft < 2000){
				    	if (isValidMove(currentRow - 1, currentCol)){
				    		if(MazeView.hasSword){
								if(swordMoveCount == 7){
				    				MazeView.direction = 8;
				        			currentRow--;
				    				swordMoveCount = 8;
				    				stepsLeft++;
				    			}else{
				    				MazeView.direction = 13;
				        			currentRow--;
				    				swordMoveCount = 7;
				    				stepsLeft++;
				    			}
				    		}else{
				    			if(moveCount <= 2){
				    				MazeView.direction = moveCount;
				        			currentRow--;
				    				moveCount++;
				    				stepsLeft++;
				    			}else{
				    				moveCount = 0;
				    				MazeView.direction = moveCount;
				        			currentRow--;
				    				moveCount++;
				    				stepsLeft++;
				    			}
				    		}
				    		updateView();
				    	}
					}else{
						new GameOver("steps");
						this.dispose();
					}
			    }
				//player down
				else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S && currentRow < MAZE_DIMENSION - 1) {
			    	if(stepsLeft < 2000){
						if (isValidMove(currentRow + 1, currentCol)){
				    		if(MazeView.hasSword){
				    			if(swordMoveCount == 7){
				    				MazeView.direction = 8;
				    				currentRow++;
				    				swordMoveCount = 8;
				    				stepsLeft++;
				    			}else{
				    				MazeView.direction = 13;
				    				currentRow++;
				    				swordMoveCount = 7;
				    				stepsLeft++;
				    			}
				    		}else {
				    			if(moveCount <= 2){
				    				MazeView.direction = moveCount;
				    				currentRow++;
				    				moveCount++;
				    				stepsLeft++;
				    			}else{
				    				moveCount = 0;
				    				MazeView.direction = moveCount;
				    				currentRow++;
				    				moveCount++;
				    				stepsLeft++;
				    			}
				    		}
				    		updateView();
				    	}
			    	}else{
			    		new GameOver("steps");
						this.dispose();
			    	}
			    }
				//player right
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D && currentCol < MAZE_DIMENSION - 1) {
					if(stepsLeft < 2000){
						if (isValidMove(currentRow, currentCol + 1)){
				    		if(MazeView.hasSword){
				    			if(swordMoveCount == 9){
				    				MazeView.direction = 9;
				    				currentCol++;
				    				swordMoveCount = 10;
				    				stepsLeft++;
				    			}else{
				    				MazeView.direction = 10;
				    				currentCol++;
				    				swordMoveCount = 9;
				    				stepsLeft++;
				    			}
				    		}else{
				        		if(sideCountRight == 3){
				    				MazeView.direction = sideCountRight;
				    				currentCol++;
				    				sideCountRight++;
				    				stepsLeft++;
				    			}else if(sideCountRight == 4){
				    				MazeView.direction = sideCountRight;
				    				currentCol++;
				    				sideCountRight--;
				    				stepsLeft++;
				    			}
				    		}
				    		updateView();
				    	}
					}else{
						new GameOver("steps");
						this.dispose();
					}
			    }
				//player left
				else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A && currentCol > 0) {
					if(stepsLeft < 2000){
						if (isValidMove(currentRow, currentCol - 1)){
				    		if(MazeView.hasSword){
				    			if(swordMoveCount == 11){
				    				MazeView.direction = 11;
				    				currentCol--;
				    				swordMoveCount = 12;
				    				stepsLeft++;
				    			}else{
				    				MazeView.direction = 12;
				    				currentCol--;
				    				swordMoveCount = 11;
				    				stepsLeft++;
				    			}
				    		}else{
					    		if(sideCountLeft == 5){
									MazeView.direction = 5;
									currentCol--;
									sideCountLeft = 6;
									stepsLeft++;
								}else if(sideCountLeft == 6){
									MazeView.direction = 6;
									currentCol--;
									sideCountLeft = 5;
									stepsLeft++;
								}
				    		}
				    		updateView();
				    	}
					}else{
						new GameOver("steps");
						this.dispose();
					}
			    }
				//toggle view
				else if (e.getKeyCode() == KeyEvent.VK_Z){
			    	view.toggleZoom();
			    }else{
			    	return;
			    }
				lastPress = System.currentTimeMillis();
    		}
    	}catch(Exception ex){
    		//catch ArrayIndexOutOfBoundEx when try to walk through of of the edge walls
    	}
          
    }
    
    public void keyReleased(KeyEvent e) {//set all images to idle
    	if(MazeView.hasSword)
    		MazeView.direction = 7;
    	else
    		MazeView.direction = 2;
    }
    
	public void keyTyped(KeyEvent e) {} //Ignore

	/**
	 * Performs collision detection and fires method calls based on a feature that is walked in to
	 * 
	 */
	private boolean isValidMove(int r, int c) throws Exception{
		if (r <= model.length - 1 && c <= model[r].length - 1 && r >= 0 & c >= 0 && model[r][c].getType() == ' '){
				model[currentRow][currentCol].setType(NodeType.NONE);
				model[r][c].setType(NodeType.PLAYER);
				return true;
		}else{
			if(model[r][c].getType() == 'W' && !MazeView.hasSword){
				model[r][c].setType(NodeType.WALL);
				MazeView.hasSword = true;
				MazeView.direction = 7;
				weaponStrength = 7;
				view.repaint();
			}
			
			if(model[r][c].getType() == 'S' && !MazeView.hasSword){
				model[r][c].setType(NodeType.WALL);
				MazeView.hasSword = true;
				MazeView.direction = 7;
				weaponStrength = 10;
				view.repaint();
			}
			
			if(model[r][c].getType() == 'T' && !MazeView.hasSword){
				model[r][c].setType(NodeType.WALL);
				MazeView.hasSword = true;
				MazeView.direction = 7;
				weaponStrength = 3;
				view.repaint();
			}
			
			if(model[r][c].getType() == 'B'){ 
				model[r][c] = new Node(r,c);
				model[r][c].setType(NodeType.NONE);
				blowUpBomb(model[r][c], 3);
			}
			
			if(model[r][c].getType() == '.' && hasKey){ 
				model[r][c] = new Node(r,c);
				model[r][c].setType(NodeType.NONE);
				new GameOver("You win");
				this.dispose();
			}
			
			if(model[r][c].getType() == 'K'){ 
				model[r][c] = new Node(r,c);
				model[currentRow][currentCol].setType(NodeType.NONE);
				model[r][c].setType(NodeType.PLAYER);
				hasKey = true;
				return true;
			}
			
			if(model[r][c].getType() == 'E'){ 
				
				double result = Fighting.fight(weaponStrength, enemiesHealth.get(model[r][c])); 
				int damage = (int) Math.round(result);
				int health = 10;
				
				health -= damage;
								
				if(health <= 0){
					model[r][c].setType(NodeType.NONE);
					MazeView.direction = 2;
					playerHealth = 100;
				}else if(health >= 1 && health <= 5){
					MazeView.direction = 2;
					playerHealth-= 25;
				}else if(health >= 5 && health <= 8){
					MazeView.direction = 2;
					playerHealth-= 75;
				}else{
					MazeView.direction = 2;
					playerHealth = 0;
				}
				
				if(playerHealth <= 0){
		        	new GameOver("health");
					this.dispose();
					MazeView.direction = 2;
		        }
				
				MazeView.hasSword = false;
				view.repaint();
			}
			
			if(model[r][c].getType() == '?'){ 
				model[r][c] = new Node(r,c);
				exitSearch();
			}
			
			return false; //Can't move
		}
	}
	
	/**
	 * Heuristic algorithm not yet implemented
	 */
	private void exitSearch() {
		
	}

	/**
	 * destroys n amount of rows and columns to it's right
	 * 
	 * @param node
	 * @param depth
	 */
	private void blowUpBomb(Node node, int depth){
		int row = node.getRow();
		int col = node.getCol();
			for(int i = 0; i < depth; i++){
				for(int x = row; x < row + 3; x++){
					for(int y = col; y < col + 3; y++){
						try{
							if(model[x][y].getType() != 'P' && model[x][y].getType() != '.')
								model[x][y].setType(NodeType.NONE);
						}catch(Exception e){
							//only throws when bomb is near corner or wall
						}
					}
				}
			}
	}
	
	public static void main(String[] args) throws Exception{
		new Runner();
	}
}