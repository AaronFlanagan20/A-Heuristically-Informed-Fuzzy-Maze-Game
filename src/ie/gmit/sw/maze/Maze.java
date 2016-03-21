package ie.gmit.sw.maze;

import ie.gmit.sw.maze.Node.NodePassage;

public class Maze {
	private Node[][] maze;
	
	public Maze(int rows, int cols){
		maze = new Node[rows][cols];
		init();
		buildMaze();
		
		//int featureNumber = (int)((rows * cols) * 0.01);
		//addFeature('W', 'X', featureNumber);
		//addFeature('?', 'X', featureNumber);
		//addFeature('B', 'X', featureNumber);
		//addFeature('H', 'X', featureNumber);
	}
	
	private void init(){
		for(int row = 0; row < maze.length; row++){
			for(int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node();
				maze[row][col].setPassage(NodePassage.X);
			}
		}
	}
		
	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < feature){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getPassageType() == replace){
				switch(feature){
					case 'W': maze[row][col].setPassage(NodePassage.W); break;
					case '?': maze[row][col].setPassage(NodePassage.Q); break;
					case 'B': maze[row][col].setPassage(NodePassage.B); break;
					case 'H': maze[row][col].setPassage(NodePassage.H); break;
				}
				counter++;
			}
		}
	}
	
	private void buildMaze(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze.length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (num >= 4 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setPassage(NodePassage.NONE);
					continue;
				}
				if (row + 1 < maze.length){ //Check south
					maze[row + 1][col].setPassage(NodePassage.NONE);
				}				
			}
		}	
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
}