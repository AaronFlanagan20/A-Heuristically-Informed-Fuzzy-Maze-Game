package ie.gmit.sw.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	public GameOver(String reason) {
		
		JPanel panel = new JPanel();
		JPanel labelPanel = new JPanel();
		
		JLabel gameOver = new JLabel("<html> Game over <br/> Reason: " + reason + "</html>");
		gameOver.setFont(new Font("ITALIC" ,0, 30));
		labelPanel.add(gameOver, BorderLayout.NORTH);

		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == play){
					dispose();
					try {
						Runner.stepsLeft = 0;
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
		getContentPane().add(labelPanel, BorderLayout.NORTH);

		setSize(400, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
