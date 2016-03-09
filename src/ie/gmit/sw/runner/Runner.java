package ie.gmit.sw.runner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ie.gmit.sw.maze.MazeGenerator;
import ie.gmit.sw.maze.MazeView;
import ie.gmit.sw.maze.Node;

public class Runner extends JFrame implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	private Node[][] maze;
	private MazeView mazeView;
	
	public Runner(){
		super("Mario Maze");
		maze = new MazeGenerator(60, 60).getMaze();
    	mazeView = new MazeView(maze);
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	addKeyListener(this);
    	setFocusable(true);//needed for keylistener
        add(mazeView);
        setSize(800,700);
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
			System.out.println("Up");
		}
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
			System.out.println("Left");
		}
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
			System.out.println("Down");
		}
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
			System.out.println("Right");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Runner();
	}
}
