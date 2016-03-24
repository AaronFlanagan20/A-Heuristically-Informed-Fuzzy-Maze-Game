package ie.gmit.sw.maze;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public enum NodeType{WALL, WEAPON, PRISONER, BOMB, PLAYER, ENEMY, EXIT, NONE};
	private NodeType type = NodeType.PLAYER;
	private List<Node> children = new ArrayList<Node>();
	private List<Node> exits = new ArrayList<Node>();
	public boolean visited =  false;
	public boolean goal;
	
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
	
	public int getChildNodeCount(){
		return children.size();
	}

	public void addChildNode(Node child){
		children.add(child);
	}
	
	public void removeChild(Node child){
		children.remove(child);
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
			return 'L';
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
		if(exits.contains(node))
			return true;
		else
			return false;
	}

	public void setGoalNode(boolean goal, Node node) {
		this.goal = goal;
		exits.add(node);
	}
}
