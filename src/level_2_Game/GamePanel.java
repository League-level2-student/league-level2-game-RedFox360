package level_2_Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	private static final long serialVersionUID = -8319762195851518912L;
	public static final int MENU = 0;
	public static final int GAME = 1;
	public static final int GAME2 = 2;
	public static final int GAME3 = 3;
	public static final int END = 4;
	public static final int WON = 5;
	public static int currentState = MENU;
	public static boolean wonGame = false;
	public static boolean lostGame = false;
	public static String endText = "Thanks for playing Alien Invasion!";
	Font titleFont;
	Font normalFont;
	Font scoreFont;
	Timer frameDraw;
	static Timer projectileTimer;
	static boolean useProjectile = false;
	boolean dialogDrawn = false;
	public static boolean gamePaused = false;
	public static Rocketship rocketShip;
	public static Car car;
	public static boolean hideMenus;
	static int timerLength = 500;
	ObjectManagerP1 manager1;
	ObjectManagerP2 manager2;

	public GamePanel() {
		titleFont = new Font("Consolas", Font.PLAIN, 50);
		normalFont = new Font("Consolas", Font.PLAIN, 24);
		frameDraw = new Timer(1000 / 60, this);
		scoreFont = new Font("Consolas", Font.PLAIN, 18);
		frameDraw.start();
		projectileTimer = new Timer(timerLength, this);
		projectileTimer.start();
		rocketShip = new Rocketship(400, 700, 60, 60, 20);
		car = new Car(700, 700, 100, 100);
		manager1 = new ObjectManagerP1(rocketShip);
		manager2 = new ObjectManagerP2(car);
		if (needImage) {
			loadImage("UFO.png");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == GAME2) {
			drawGame2State(g);
		} else if (currentState == GAME3) {
			drawGame3State(g);
		} else if (currentState == END) {
			drawEndState(g);
		} else {
			drawWonState(g);
		}
	}

	public static void halfTimer() {
		useProjectile = true;
		timerLength /= 2;
		projectileTimer.setDelay(timerLength);
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
		manager1.draw(g);
		g.setColor(Color.BLUE);
		g.setFont(scoreFont);
		if (!hideMenus) {
			g.drawString("Rocket phase", 20, 20);
			g.drawString("Score: " + manager1.getScore(), 20, 50);
			g.drawString("Alien Speed: " + manager1.getSpeed(), 20, 70);
			g.drawString("Aliens who got to Earth: " + manager1.getFallenAliens(), 20, 90);
		}
		if (gamePaused) {
			g.setFont(normalFont);
			g.drawString("GAME PAUSED", 300, 400);
		}
	}

	public void drawGame2State(Graphics g) {
		manager2.draw(g);
		g.setColor(Color.BLUE);
		g.setFont(scoreFont);
		if (!hideMenus) {
			g.drawString("Car phase", 20, 20);
			g.drawString("Score: " + manager2.getScore(), 20, 50);
			g.drawString("Alien Speed: " + manager2.getSpeed(), 20, 70);
			g.drawString("Time: " + manager2.getTime(), 20, 90);
		}
		if (gamePaused) {
			g.setFont(normalFont);
			g.drawString("GAME PAUSED", 300, 400);
		}
	}

	public void drawGame3State(Graphics g) {
		if (gotImage) {
			g.drawImage(image, 0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT, null);
			new AlienLeader().draw(g, 250, 100, 300, 500);
		} else {
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		}
	}

	public void drawDialog() {
		if (!dialogDrawn) {
			JOptionPane.showMessageDialog(this,
					"The alien leader sees that you are a great warrior, but he will not leave Earth alone until you prove your intelligence.");
			dialogDrawn = true;
		}
	}

	public void drawEndState(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		g.setColor(Color.YELLOW);
		g.setFont(titleFont);
		g.drawString("Game Over", 275, 150);
		g.setFont(normalFont);
		g.drawString(endText, 200, 250);
		g.drawString("The world is doomed...", 250, 400);
		g.drawString("Your final score is " + manager2.getScore(), 250, 550);
		g.drawString("Press ENTER to restart", 250, 700);
	}

	public void drawWonState(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		g.drawString("You Won!", 300, 200);
		g.setFont(normalFont);
		g.drawString("Thanks for playing Alien Invasion!", 200, 350);
		g.drawString("Your final score is " + manager2.getScore(), 250, 500);
		g.drawString("Press ENTER to restart", 250, 650);
	}

	public void updateGameState() {
		if (!gamePaused) {
			manager1.update();
		}
		if (rocketShip.isActive == false) {
			currentState++;
			manager1.speed = 1;
			manager1.aliens.clear();
			manager1.projectiles.clear();
			manager1.asteroids.clear();
			manager1.speed = 1;
			manager2.speed = 1;
			manager2.aliens.clear();
			manager1.powerups.clear();
			manager2.powerups.clear();
			manager2.setScore(manager1.getScore());
			manager2.start();
			timerLength = 500;
			projectileTimer.setDelay(timerLength);
		}
	}

	public void updateGame2State() {
		if (!gamePaused) {
			manager2.update();
		}
		if (car.isActive == false) {
			currentState++;
			manager2.speed = 1;
			manager2.aliens.clear();
			manager2.bullets.clear();
		}
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}

	void clearAll() {
		manager1.aliens.clear();
		manager1.projectiles.clear();
		manager1.asteroids.clear();
		manager1.powerfulAliens.clear();
		manager1.powerfulAliensBullets.clear();
		manager2.aliens.clear();
		manager1.powerups.clear();
		manager2.powerups.clear();
		manager2.bullets.clear();
		manager1.speed = 1;
		manager2.speed = 1;
	}
	
	public static void setAlienSpawnRate(int rateSecs) {
		int rate = rateSecs*1000;
		ObjectManagerP1.alienSpawn.setDelay(rate);
		ObjectManagerP2.alienSpawn.setDelay(rate);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == WON || currentState == END) {
				rocketShip.isActive = true;
				car.isActive = true;
				rocketShip.update();
				car.update();
				endText = "";
				rocketShip = new Rocketship(400, 700, 60, 60, 20);
				car = new Car(700, 700, 100, 100);
				manager1 = new ObjectManagerP1(rocketShip);
				manager2 = new ObjectManagerP2(car);
				clearAll();
				currentState = MENU;
			} else if (currentState == MENU) {
				clearAll();
				currentState = GAME;
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
			if (currentState == WON) {
				currentState = MENU;
			} else {
				currentState++;
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
			if (currentState == MENU) {
				currentState = WON;
			} else {
				currentState--;
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyChar() == 'w') {
			if (currentState == GAME && rocketShip.y > 0) {
				rocketShip.up();
			} else if (currentState == GAME2 && car.y > 0) {
				car.up();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN || arg0.getKeyChar() == 's') {
			if (currentState == GAME && rocketShip.y < AlienInvasion.HEIGHT - 60) {
				rocketShip.down();
			} else if (currentState == GAME2 && car.y < AlienInvasion.HEIGHT - 100) {
				car.down();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyChar() == 'a') {
			if (currentState == GAME && rocketShip.x > 0) {
				rocketShip.left();
			} else if (currentState == GAME2 && car.x > 0) {
				car.left();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyChar() == 'd') {
			if (currentState == GAME && rocketShip.x < AlienInvasion.WIDTH - 60) {
				rocketShip.right();
			} else if (currentState == GAME2 && car.x < AlienInvasion.WIDTH - 100) {
				car.right();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE && useProjectile) {
			if (currentState == GAME) {
				manager1.addProjectile(rocketShip.getProjectile());
			} else if (currentState == GAME2) {
				manager2.addBullet(car.getBullet());
			} else if (currentState == MENU) {
				JOptionPane.showMessageDialog(null,
						"GAME CONTROLS: Press the arrow keys or use WASD to move around    Use space to shoot projectiles at aliens.\n"
						+ "OTHER CONTROLS: Press escape to pause the game    Press F3 to hide the menus."
						+ "GOAL: Battle aliens in space and on Earth, and when you finish, answer a riddle to win the game.\n"
						+ "Developed by Sameer Prakash 2020\n"
						+ "View code on Github: https://github.com/League-level2-student/league-level2-game-RedFox360");
			}
			useProjectile = false;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (gamePaused) {
				gamePaused = false;
			} else if (!gamePaused) {
				gamePaused = true;
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_1) {
			if (hideMenus) {
				hideMenus = false;
			} else if (!hideMenus) {
				hideMenus = true;
			}
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
			this.updateGameState();
		} else if (currentState == GAME2) {
			this.updateGame2State();
		}
		if (arg0.getSource() == projectileTimer) {
			useProjectile = true;
		}
		repaint();
	}
}