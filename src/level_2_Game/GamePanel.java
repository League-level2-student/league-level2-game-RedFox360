package level_2_Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;



public class GamePanel extends JPanel implements ActionListener, KeyListener {
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	final int WON = 3;
	int currentState = MENU;
	boolean wonGame = false;
	Font titleFont;
	Font normalFont;
	Timer frameDraw;
	Timer alienSpawn;
	Rocketship rocketShip;
	ObjectManagerP1 manager1;

	GamePanel() {
		titleFont = new Font("Consolas", Font.PLAIN, 50);
		normalFont = new Font("Consolas", Font.PLAIN, 24);
		frameDraw = new Timer(1000/60,this);
		frameDraw.start();
		rocketShip = new Rocketship(400, 700, 50, 50, 20);
		manager1 = new ObjectManagerP1(rocketShip);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		} else if (currentState == WON) {
			drawWonState(g);
		}
	}

	public void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		g.drawString("ALIEN INVASION", 200, 200);
		g.setFont(normalFont);
		g.drawString("Press ENTER to start", 250, 400);
		g.drawString("Press SPACE for instructions", 200, 600);
	}

	public void drawGameState(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		manager1.draw(g);
	}

	public void drawEndState(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		g.setColor(Color.YELLOW);
		g.setFont(titleFont);
		g.drawString("Game Over", 275, 200);
		g.setFont(normalFont);
		g.drawString("Thanks for playing Alien Invasion!", 200, 400);
		g.drawString("Press ENTER to restart", 250, 600);
	}
	public void drawWonState(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		g.drawString("You Won!", 300, 200);
		g.setFont(normalFont);
		g.drawString("Thanks for playing Alien Invasion!", 200, 400);
		g.drawString("Press ENTER to restart", 250, 600);
	}

	public void updateGameState() {
		manager1.update();
	}
	public void startPhase1() {
		alienSpawn = new Timer(2000, manager1);
		alienSpawn.start();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode()==KeyEvent.VK_ENTER) {
		    if (currentState == WON) {
		        currentState = MENU;
		    } else if (currentState == MENU) {
		    	currentState = GAME;
		    	startPhase1();
		    } else {
		        currentState++;
		    }
		}
		if (arg0.getKeyCode()==KeyEvent.VK_UP) {
			rocketShip.up();
		}
		if (arg0.getKeyCode()==KeyEvent.VK_DOWN) {
			rocketShip.down();
		}
		if (arg0.getKeyCode()==KeyEvent.VK_LEFT) {
			rocketShip.left();
		}
		if (arg0.getKeyCode()==KeyEvent.VK_RIGHT) {
			rocketShip.right();
		} 
		if (arg0.getKeyCode()==KeyEvent.VK_SPACE && currentState == GAME) {
			manager1.addProjectile(rocketShip.getProjectile());
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (currentState == GAME) {
			updateGameState();
		}
		repaint();
	}
}