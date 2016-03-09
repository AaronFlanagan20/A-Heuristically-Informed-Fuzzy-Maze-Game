package ie.gmit.sw.maze;

import java.util.Random;

import ie.gmit.sw.maze.Node.NodeType;

public class MazeGenerator {
	private Node[][] maze;
	
	public Node[][] getMaze() {
		return maze;
	}
	
	public MazeGenerator(int rows, int cols) {
		maze = new Node[rows][cols];
		generateMaze();
		setGoalNode();
	}

	private void generateMaze(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node(row, col);
				int num = (int) (Math.random() * 10);
				if (col > 0 && (row == 0 || num >= 5)){
					maze[row][col].setPassage(Node.NodePassage.West);
				}else{
					maze[row][col].setPassage(Node.NodePassage.North);
				}
			}
		}
	}
	
	private void setGoalNode() {
		Random generator = new Random();
		int randRow = generator.nextInt(maze.length);
		int randCol = generator.nextInt(maze[0].length);
		maze[randRow][randCol].setGoalNode(true);
		maze[randRow][randCol].setType(NodeType.Princess);
	}

}
