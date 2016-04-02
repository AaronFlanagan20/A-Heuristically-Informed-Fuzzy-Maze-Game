package ie.gmit.sw.maze;

import java.util.ArrayList;

import ie.gmit.sw.maze.Node.NodeType;

public class Maze {
	private Node[][] maze;
	private ArrayList<MazePoint> mazePoints;
	
	public Maze(int rows, int cols){
		maze = new Node[rows][cols];
		init();
		buildMaze();
		
		addFeature('W', 'X');
		addFeature('T', 'X');
		addFeature('S', 'X');
		addFeature('?', 'X');
		addFeature('B', 'X');
		addFeature('H', 'X');
	}
	
	private void init(){
		for(int row = 0; row < maze.length; row++){
			for(int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node(row,col);
				maze[row][col].setType(NodeType.WALL);
			}
		}
	}
		
	private void addFeature(char feature, char replace){
		for(int r =0; r < 60; r++){
			
			int row = (int) (Math.random() * maze.length);
			int col = (int) (Math.random() * maze.length);
			
			if (maze[row][col].getType() == replace && maze[row][col].getType() != ' '){
				if(feature == 'L' && r <=3){
					maze[row][col].setType(NodeType.SWORD);
					break;
				}
				
				switch(feature){
					case 'W': maze[row][col].setType(NodeType.SWORD); break;
					case 'S': maze[row][col].setType(NodeType.SPRAY); break;
					case 'T': maze[row][col].setType(NodeType.TOOTHPICK); break;
					case '?': maze[row][col].setType(NodeType.PRISONER); break;
					case 'B': maze[row][col].setType(NodeType.BOMB); break;
				}
			}
		}
	}
	
	private void buildMaze(){
		
		// Adapted from http://jonathanzong.com/blog/2012/11/06/maze-generation-with-prims-algorithm
		//example on Prims algorithm
		MazePoint st = new MazePoint((int)(Math.random()* maze.length),(int)(Math.random()* maze.length),null);
		
		maze[st.r][st.c] = new Node(st.r, st.c);
		maze[st.r][st.c].setStart(maze[st.r][st.c]);
		
		mazePoints = new ArrayList<MazePoint>();
		// iterate through direct neighbours of node
        for(int x=-1;x<=1;x++)
        	for(int y=-1;y<=1;y++){
        		if(x==0&&y==0||x!=0&&y!=0)
        			continue;
        		try{
        			if(maze[st.r+x][st.c+y].getType() == ' ') continue;
        		}catch(Exception e){ // ignore ArrayIndexOutOfBounds
        			continue;
        		}
        		// add eligible points to frontier
        		mazePoints.add(new MazePoint(st.r+x,st.c+y,st));
        	}
        
        MazePoint last=null;
        while(!mazePoints.isEmpty()){
        	// pick current node at random
        	MazePoint currentPoint = mazePoints.remove((int)(Math.random()*mazePoints.size()));
        	MazePoint oppositePoint = currentPoint.opposite();
        	
        	try{
        		// if both node and its opposite are walls
        		if(maze[currentPoint.r][currentPoint.c].getType() == 'X' && maze[oppositePoint.r][oppositePoint.c].getType() == 'X'){

    				// open path between the nodes
    				maze[currentPoint.r][currentPoint.c].setType(NodeType.NONE);
    				maze[oppositePoint.r][oppositePoint.c].setType(NodeType.NONE);
    				
    				

    				// store last node in order to mark it later
    				last = oppositePoint;

    				// iterate through direct neighbours of node, same as earlier
    				for(int row=-1;row<=1;row++)
			        	for(int col=-1;col<=1;col++){
			        		if(row==0&&col==0||row!=0&&col!=0)
			        			continue;
			        		try{
			        			if(maze[oppositePoint.r+row][oppositePoint.c+col].getType() == ' ') continue;
			        		}catch(Exception e){
			        			continue;
			        		}
			        		mazePoints.add(new MazePoint(oppositePoint.r+row,oppositePoint.c+col,oppositePoint));
			        	}
        		}
        	}catch(Exception e){ // ignore NullPointer and ArrayIndexOutOfBounds
        	}

        	// if algorithm has resolved, mark end node
        	if(mazePoints.isEmpty())
        		maze[last.r][last.c].setGoalNode(maze[last.r][last.c]);
        }

		// print final maze
		for(int i=0;i<maze.length;i++){
			for(int j=0;j<maze[i].length;j++)
				System.out.print(maze[i][j].getType());
			System.out.println();
		}
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
	
	private class MazePoint{
    	private Integer r;
    	private Integer c;
    	private MazePoint parent;
    	
    	public MazePoint(int x, int y, MazePoint p){
    		r=x;c=y;parent=p;
    	}
    	// compute opposite node given that it is in the other direction from the parent
    	public MazePoint opposite(){
    		if(this.r.compareTo(parent.r)!=0)
    			return new MazePoint(this.r+this.r.compareTo(parent.r),this.c,this);
    		if(this.c.compareTo(parent.c)!=0)
    			return new MazePoint(this.r,this.c+this.c.compareTo(parent.c),this);
    		return null;
    	}
    }
}