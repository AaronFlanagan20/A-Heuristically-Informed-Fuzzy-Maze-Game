package ie.gmit.sw.maze;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public enum NodePassage{X, W, Q, B, H, P, E, NONE};
	private NodePassage passage = NodePassage.X;
	private List<Node> children = new ArrayList<Node>();
	public boolean visited =  false;
	public boolean goal;
	private int row, col;
	
	public NodePassage getPassage() {
		return passage;
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
	
	public int getChildNodeCount(){
		return children.size();
	}

	public void addChildNode(Node child){
		children.add(child);
	}
	
	public void removeChild(Node child){
		children.remove(child);
	}
	
	public char getPassageType(){
		if(passage == NodePassage.X){
			return 'X';
		}if(passage == NodePassage.W){
			return 'W';
		}if(passage == NodePassage.Q){
			return '?';
		}if(passage == NodePassage.B){
			return 'B';
		}if(passage == NodePassage.H){
			return 'H';
		}if(passage == NodePassage.P){
			return 'P';
		}if(passage == NodePassage.E){
			return 'E';
		}else{
			return ' ';
		}
	}

	public void setPassage(NodePassage passage) {
		this.passage = passage;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean isGoalNode() {
		return goal;
	}

	public void setGoalNode(boolean goal) {
		this.goal = goal;
	}
}
