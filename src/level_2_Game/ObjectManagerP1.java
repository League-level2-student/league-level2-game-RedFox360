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
import javax.swing.Timer;

public class ObjectManagerP1 implements ActionListener {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	int score = 0;
	int speed = 1;
	int aliensWhoFellBackToEarth = 0;
	Timer alienSpawn;
	Rocketship rocketShip;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	Random random = new Random();
	Timer increaseSpeed;
	Timer asteroidSpawn;

	ObjectManagerP1(Rocketship r) {
		rocketShip = r;
		if (needImage) {
			loadImage("space.png");
		}
		alienSpawn = new Timer(1000, this);
		alienSpawn.start();
		asteroidSpawn = new Timer(6000, this);
		asteroidSpawn.start();
		increaseSpeed = new Timer(10000, this);
		increaseSpeed.start();
	}

	void addAlien() {
		aliens.add(new Alien(random.nextInt(AlienInvasion.WIDTH), 0, 50, 50, speed));
	}

	void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	void addAsteroid() {
		asteroids.add(new Asteroid(random.nextInt(AlienInvasion.WIDTH), 0, 80, 80));
	}


	public void update() {
		for (Iterator<Alien> iterator = aliens.iterator(); iterator.hasNext();) {
			Alien alien = iterator.next();
			alien.update();
			if (alien.y > AlienInvasion.HEIGHT) {
				alien.isActive = false;
				aliensWhoFellBackToEarth += 1;
			}
		}
		for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext();) {
			Projectile projectile = iterator.next();
			projectile.update();
			if (projectile.y > AlienInvasion.HEIGHT) {
				projectile.isActive = false;
				score += 1;
			}
		}
		for (Iterator<Asteroid> iterator = asteroids.iterator(); iterator.hasNext();) {
			Asteroid asteroid = iterator.next();
			asteroid.update();
			if (asteroid.y > AlienInvasion.HEIGHT) {
				asteroid.isActive = false;
			}
		}
		rocketShip.update();
		checkCollision();
		purgeObjects();
	}

	int getScore() {
		return this.score;
	}

	int getSpeed() {
		return this.speed;
	}

	int getFallenAliens() {
		return this.aliensWhoFellBackToEarth;
	}

	public void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, 0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT, null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, AlienInvasion.WIDTH, AlienInvasion.HEIGHT);
		}
		rocketShip.draw(g);
		for (Iterator<Alien> iterator = aliens.iterator(); iterator.hasNext();) {
			iterator.next().draw(g);

		}
		for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext();) {
			iterator.next().draw(g);
		}
		for (Iterator<Asteroid> iterator = asteroids.iterator(); iterator.hasNext();) {
			iterator.next().draw(g);
		}
	}

	public void purgeObjects() {
		for (int i = aliens.size() - 1; i >= 0; i--) {
			Alien alien = aliens.get(i);
			if (!alien.isActive) {
				aliens.remove(i);
			}
		}
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile projectile = projectiles.get(i);
			if (!projectile.isActive) {
				projectiles.remove(i);
			}
		}
		for (int i = asteroids.size() - 1; i >= 0; i--) {
			Asteroid asteroid = asteroids.get(i);
			if (!asteroid.isActive) {
				asteroids.remove(i);
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

	public void checkCollision() {
		for (Iterator<Alien> iterator = aliens.iterator(); iterator.hasNext();) {
			Alien alien = iterator.next();
			if (rocketShip.collisionBox.intersects(alien.collisionBox)) {
				rocketShip.isActive = false;
				alien.isActive = false;
			}
			for (Iterator<Projectile> jterator = projectiles.iterator(); jterator.hasNext();) {
				Projectile projectile = jterator.next();
				if (projectile.collisionBox.intersects(alien.collisionBox)) {
					projectile.isActive = false;
					alien.isActive = false;
					score += 1;
				}
				for (Iterator<Asteroid> jiterator = asteroids.iterator(); jiterator.hasNext();) {
					Asteroid asteroid = jiterator.next();
					if (projectile.collisionBox.intersects(asteroid.collisionBox)) {
						projectile.isActive = false;
					}
				}
				
			}
			for (Iterator<Asteroid> jterator = asteroids.iterator(); jterator.hasNext();) {
				Asteroid asteroid = jterator.next();
				if (asteroid.collisionBox.intersects(alien.collisionBox)) {
					alien.isActive = false;
				}
				if (asteroid.collisionBox.intersects(rocketShip.collisionBox)) {
					rocketShip.isActive = false;
					GamePanel.endText = "Your rocket was hit by an asteroid";
					GamePanel.currentState = GamePanel.GAME3;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == increaseSpeed) {
			speed += 1;
		}
		if (arg0.getSource() == alienSpawn) {
			addAlien();
		}
		if (arg0.getSource() == asteroidSpawn) {
			addAsteroid();
		}
	}
}
