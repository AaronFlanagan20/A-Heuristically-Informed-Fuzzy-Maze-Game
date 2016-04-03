package ie.gmit.sw.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ie.gmit.sw.runner.Runner;

/**
 * 
 * GameOver is just a quick screen to show the game is finished and allow the user to replay
 * 
 * @author Aaron - G00330035
 *
 */
public class GameOver extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public GameOver() {
		
		JPanel panel = new JPanel();

		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == play){
					dispose();
					try {
						new Runner();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		panel.add(play, BorderLayout.WEST);
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == exit){
					dispose();
					System.exit(0);
				}
			}
		});
		panel.add(exit, BorderLayout.EAST);
		
		getContentPane().add(panel, BorderLayout.SOUTH);

		setSize(700, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 60)); 
		g2.drawString("Game Over", 200, 200);
	}
	
	public static void main(String[] args) {
		new GameOver();
	}

}
