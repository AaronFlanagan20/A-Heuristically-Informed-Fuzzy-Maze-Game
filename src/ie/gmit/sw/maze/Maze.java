package ie.gmit.sw.maze;

import ie.gmit.sw.maze.Node.NodeType;

public class Maze {
	private Node[][] maze;
	
	public Maze(int rows, int cols){
		maze = new Node[rows][cols];
		init();
		buildMaze();
		
		addFeature('W', 'X');
		addFeature('?', 'X');
		addFeature('B', 'X');
		addFeature('H', 'X');
	}
	
	private void init(){
		for(int row = 0; row < maze.length; row++){
			for(int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node();
				maze[row][col].setType(NodeType.WALL);
			}
		}
	}
		
	private void addFeature(char feature, char replace){
		for(int r =0; r < 100; r++){
			
			int row = (int) (Math.random() * maze.length);
			int col = (int) (Math.random() * maze.length);
			
			if (maze[row][col].getType() == replace){
				if(feature == 'L' && r <=3){
					maze[row][col].setType(NodeType.WEAPON);
					break;
				}
				switch(feature){
					case 'W': maze[row][col].setType(NodeType.WEAPON); break;
					case '?': maze[row][col].setType(NodeType.PRISONER); break;
					case 'B': maze[row][col].setType(NodeType.BOMB); break;
				}
			}
		}
	}
	
	private void buildMaze(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				int num = (int) (Math.random() * 10);
				if (num >= 4 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setType(NodeType.NONE);
					continue;
				}
				if (row + 1 < maze.length){ //Check south
					maze[row + 1][col].setType(NodeType.NONE);
				}				
			}
		}	
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
}