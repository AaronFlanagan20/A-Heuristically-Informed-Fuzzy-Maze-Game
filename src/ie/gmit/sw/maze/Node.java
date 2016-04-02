package ie.gmit.sw.maze;

public class Node {
	
	public enum NodeType{WALL, TOOTHPICK, SPRAY, SWORD, PRISONER, BOMB, PLAYER, ENEMY, EXIT, NONE};
	private NodeType type = NodeType.NONE;
	public boolean visited =  false;
	public Node goal;
	public Node start;
	private int row;
	private int col;
		
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public void setStart(Node start) {
		this.start = start;
	}
	
	public Node getStart() {
		return start;
	}
	
	public Node[] children(Node[][] maze){
		
		Node[] children = new Node[maze.length];
		if (col - 1 >=0 && type == NodeType.WALL)
			children[0] = maze[row][col - 1]; //A West edge
		if (col + 1 < maze[row].length && maze[row][col + 1].getType() == 'X')
			children[1] = maze[row][col + 1]; //An East Edge
		if (row - 1 >= 0 && type == NodeType.WALL)
			children[2] = maze[row - 1][col]; //A North edge
		if (row + 1 < maze.length && maze[row + 1][col].getType() == 'X')
			children[3] = maze[row + 1][col]; //An South Edge
	
		int counter = 0;
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) counter++;
		}
		
		Node[] tmp = new Node[counter];
		int index = 0;
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null){
				tmp[index] = children[i];
				index++;
			}
		}

		return tmp;
	}
	
	public char getType(){
		if(type == NodeType.WALL){
			return 'X';
		}if(type == NodeType.SWORD){
			return 'W';
		}if(type == NodeType.SPRAY){
			return 'S';
		}if(type == NodeType.TOOTHPICK){
			return 'T';
		}if(type == NodeType.PRISONER){
			return '?';
		}if(type == NodeType.BOMB){
			return 'B';
		}if(type == NodeType.PLAYER){
			return 'P';
		}if(type == NodeType.ENEMY){
			return 'E';
		}if(type == NodeType.EXIT){
			return '.';
		}else{
			return ' ';
		}
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public boolean isGoalNode(Node node) {
		if(node == goal)
			return true;
		else 
			return false;
	}

	public void setGoalNode(Node goal) {
		goal.setType(NodeType.EXIT);
		this.goal = goal;
	}
}
