package level_2_Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ObjectManagerP2 implements ActionListener {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	Car car;
	int speed;
	int score;
	int aliensKilled = 0;
	int aliensWhoGotAway = 0;
	Timer increaseSpeed;
	Timer alienSpawn;
	Random random = new Random();
	ArrayList<Alien2> aliens =  new ArrayList<Alien2>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public ObjectManagerP2(Car c) {
		car = c;
		alienSpawn = new Timer(1000, this);
		increaseSpeed = new Timer(20000, this);
		increaseSpeed.start();
		alienSpawn.start();
		speed = 1;
		if (needImage) {
		    loadImage ("phase2background.jpg");
		}
	}
	void setScore(int score) {
		this.score = score;
	}
	public void clearAll() {
		for (int i = aliens.size() - 1; i >= 0; i++) {
			aliens.remove(i);
		}
		for (int i = bullets.size() - 1; i >= 0; i++) {
			bullets.remove(i);
		}
	}
	int getScore() {
		return score;
	}
	int getSpeed() {
		return speed;
	}
	public void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, 0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT, null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		}
		car.draw(g);
		for(Iterator<Alien2> iterator = aliens.iterator(); iterator.hasNext();) {
			iterator.next().draw(g);
		}
		for(Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
			iterator.next().draw(g);
		}
	}
	public void update() {
		for (Iterator<Alien2> iterator = aliens.iterator(); iterator.hasNext();) {
			Alien2 alien = iterator.next();
			alien.update(car);
			if (alien.x < 0) {
				alien.isActive = false;
				aliensWhoGotAway += 1;
			}
		}
		for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
			Bullet projectile = iterator.next();
			projectile.update();
			if (projectile.y > AlienInvasion.WIDTH) {
				projectile.isActive = false;
				score += 1;
			}
		}
		car.update();
		if (aliensWhoGotAway >= 20) { 
			GamePanel.endText = "Too many Aliens slipped away!";
			car.isActive = false;
			GamePanel.currentState++;
		}
		if (aliensKilled >= 20) {
			car.isActive = false;
			GamePanel.currentState++;
			JOptionPane.showMessageDialog(null, "The alien leader sees you are a great warrior, but he will not leave Earth alone until you prove your intelligence.");
			String riddleGuess = JOptionPane.showInputDialog(null, "Which is better? Java or Python");
			if (riddleGuess.equalsIgnoreCase("java") || riddleGuess.equalsIgnoreCase("python")) {
				JOptionPane.showMessageDialog(null, "Correct!");
				GamePanel.currentState = GamePanel.WON;
			} else {
				GamePanel.endText = "You answered the riddle incorrectly...";
				GamePanel.currentState = GamePanel.END;
			}
		}
		checkCollision();
		purgeObjects();
	}
	public void purgeObjects() {
		for(int i = aliens.size() - 1; i >= 0; i--) {
			Alien2 alien = aliens.get(i);
			if (!alien.isActive) {
				aliens.remove(i);
			}
		}
		for (int i = bullets.size() - 1; i >= 0; i--) {
			Bullet bullet = bullets.get(i);
			if (!bullet.isActive) {
				bullets.remove(i);
			}
		}
	}
	public void addAlien() {
		aliens.add(new Alien2(0, random.nextInt(AlienInvasion.WIDTH), 50, 50, speed));
	}
	public void addBullet(Bullet b) {
		bullets.add(b);
	}
	public void checkCollision() {
		for (Iterator<Alien2> iterator = aliens.iterator(); iterator.hasNext();) {
			Alien2 alien = iterator.next();
			if (car.collisionBox.intersects(alien.collisionBox)) {
				car.isActive = false;
				GamePanel.endText = "Your car was hit by an alien.";
				GamePanel.currentState = GamePanel.GAME3;
				alien.isActive = false;
			}
			for (Iterator<Bullet> jterator = bullets.iterator(); jterator.hasNext();) {
				Bullet projectile = jterator.next();
				if (projectile.collisionBox.intersects(alien.collisionBox)) {
					projectile.isActive = false;
					alien.isActive = false;
					score += 1;
					aliensKilled += 1;
				}
			}
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
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == increaseSpeed) {
			speed+=1;
		}
		if (arg0.getSource() == alienSpawn) {
			addAlien();
		}
	}

}
