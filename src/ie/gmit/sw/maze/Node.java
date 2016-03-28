package ie.gmit.sw.maze;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public enum NodeType{WALL, WEAPON, PRISONER, BOMB, PLAYER, ENEMY, EXIT, NONE};
	private NodeType type = NodeType.PLAYER;
	private List<Node> children = new ArrayList<Node>();
	public boolean visited =  false;
	public Node goal;
	public Node start;
	int row;
	int col;
	
	public void setStart(Node start) {
		this.start = start;
	}
	
	public Node getStart() {
		return start;
	}
	
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

	public Node[] children(){
		return (Node[]) children.toArray(new Node[children.size()]);
	}
	
	public boolean isLeaf(){
		if (children.size() > 0){
			return false;
		}else{
			return true;
		}
	}
	
	public char getType(){
		if(type == NodeType.WALL){
			return 'X';
		}if(type == NodeType.WEAPON){
			return 'W';
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
