package level_2_Game;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AlienInvasion {
	static JFrame frame;
	GamePanel panel;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static void main(String[] args) {
		AlienInvasion gameClass = new AlienInvasion();
		gameClass.setup();
	}
	AlienInvasion() {
		frame = new JFrame();
		panel = new GamePanel();
	}
	public void setup() {
		frame.setVisible(true);
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(panel);
		frame.setResizable(false);
	}
	
}