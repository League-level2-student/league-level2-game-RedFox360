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

public class ObjectManagerP1 implements ActionListener {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	int score = 0;
	int speed = 1;
	int aliensWhoFellBackToEarth = 0;
	Rocketship rocketShip;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	ArrayList<TimerPowerup> powerups = new ArrayList<TimerPowerup>();
	ArrayList<PowerfulAlien> powerfulAliens = new ArrayList<PowerfulAlien>();
	ArrayList<PowerfulAlienBullet> powerfulAliensBullets = new ArrayList<PowerfulAlienBullet>();
	Random random = new Random();
	public static Timer alienSpawn;
	public static Timer powerfulAlienSpawn;
	public static Timer powerfulAlienBulletSpawn;
	public static Timer increaseSpeed;
	public static Timer asteroidSpawn;
	public static Timer powerupSpawn;

	public ObjectManagerP1(Rocketship r) {
		rocketShip = r;
		if (needImage) {
			loadImage("space.png");
		}
		alienSpawn = new Timer(1000, this);
		alienSpawn.start();
		asteroidSpawn = new Timer(6000, this);
		asteroidSpawn.start();
		increaseSpeed = new Timer(20000, this);
		increaseSpeed.start();
		powerupSpawn = new Timer(15000, this);
		powerupSpawn.start();
		powerfulAlienSpawn = new Timer(3000, this);
		powerfulAlienSpawn.start();
		powerfulAlienBulletSpawn = new Timer(4000, this);
		powerfulAlienBulletSpawn.start();
	}

	void addAlien() {
		aliens.add(new Alien(random.nextInt(AlienInvasion.WIDTH), 0, 50, 50, speed));
	}

	void addPowerfulAlien() {
		powerfulAliens.add(new PowerfulAlien(random.nextInt(AlienInvasion.WIDTH), 0, 50, 50, speed));
	}

	void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	void addAsteroid() {
		asteroids.add(new Asteroid(random.nextInt(AlienInvasion.WIDTH), 0, 80, 80));
	}

	void addPowerup() {
		powerups.add(
				new TimerPowerup(random.nextInt(AlienInvasion.WIDTH), random.nextInt(AlienInvasion.HEIGHT), 100, 100));
	}

	public void update() {
		for (Iterator<Alien> iterator = aliens.iterator(); iterator.hasNext();) {
			Alien alien = iterator.next();
			alien.update();
			if (alien.y > AlienInvasion.HEIGHT) {
				alien.isActive = false;
				aliensWhoFellBackToEarth += 1;
				score -= 1;
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
		for (Iterator<TimerPowerup> iterator = powerups.iterator(); iterator.hasNext();) {
			iterator.next().update();
		}
		for (Iterator<PowerfulAlien> iterator = powerfulAliens.iterator(); iterator.hasNext();) {
			PowerfulAlien alien = iterator.next();
			alien.update();
			if (alien.y > AlienInvasion.HEIGHT) {
				alien.isActive = false;
				aliensWhoFellBackToEarth += 1;
				if (score != 0) {
					score -= 1;
				}
			}
		}
		for (Iterator<PowerfulAlienBullet> iterator = powerfulAliensBullets.iterator(); iterator.hasNext();) {
			PowerfulAlienBullet bullet = iterator.next();
			bullet.update();
			if (bullet.y > AlienInvasion.HEIGHT) {
				bullet.isActive = false;
			}
		}
		rocketShip.update();
		checkCollision();
		purgeObjects();
	}

	void dialog() {
		JOptionPane.showMessageDialog(null, "Your car was hit! It's falling back down to Earth...");
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
		for (Iterator<TimerPowerup> iterator = powerups.iterator(); iterator.hasNext();) {
			iterator.next().draw(g);
		}
		for (Iterator<PowerfulAlien> iterator = powerfulAliens.iterator(); iterator.hasNext();) {
			iterator.next().draw(g);
		}
		for (Iterator<PowerfulAlienBullet> iterator = powerfulAliensBullets.iterator(); iterator.hasNext();) {
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
		for (int i = powerups.size() - 1; i >= 0; i--) {
			TimerPowerup powerup = powerups.get(i);
			if (!powerup.isActive) {
				powerups.remove(i);
			}
		}
		for (int i = powerfulAliens.size() - 1; i >= 0; i--) {
			PowerfulAlien alien = powerfulAliens.get(i);
			if (!alien.isActive) {
				powerfulAliens.remove(i);
			}
		}
		for (int i = powerfulAliensBullets.size() - 1; i >= 0; i--) {
			PowerfulAlienBullet bullet = powerfulAliensBullets.get(i);
			if (!bullet.isActive) {
				powerfulAliensBullets.remove(i);
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
					GamePanel.currentState = GamePanel.END - 1;
				}
			}
		}
		for (Iterator<TimerPowerup> iterator = powerups.iterator(); iterator.hasNext();) {
			TimerPowerup powerup = iterator.next();
			if (powerup.collisionBox.intersects(rocketShip.collisionBox)) {
				powerup.isActive = false;
				GamePanel.halfTimer();
			}
		}
		for (Iterator<PowerfulAlien> iterator = powerfulAliens.iterator(); iterator.hasNext();) {
			PowerfulAlien alien = iterator.next();
			if (alien.collisionBox.intersects(rocketShip.collisionBox)) {
				rocketShip.isActive = false;

				GamePanel.endText = "Your rocket was hit by a powerful alien.";
				GamePanel.currentState = GamePanel.END - 1;
			}
			for (Iterator<Projectile> jterator = projectiles.iterator(); jterator.hasNext();) {
				Projectile projectile = jterator.next();
				if (alien.collisionBox.intersects(projectile.collisionBox)) {
					alien.isActive = false;
					projectile.isActive = false;
				}
			}
			for (Iterator<Asteroid> jterator = asteroids.iterator(); jterator.hasNext();) {
				Asteroid asteroid = jterator.next();
				if (alien.collisionBox.intersects(asteroid.collisionBox)) {
					alien.isActive = false;
				}
			}
		}
		for (Iterator<PowerfulAlienBullet> iterator = powerfulAliensBullets.iterator(); iterator.hasNext();) {
			PowerfulAlienBullet bullet = iterator.next();
			if (bullet.collisionBox.intersects(rocketShip.collisionBox)) {
				rocketShip.isActive = false;

			}
			for (Iterator<Projectile> jterator = projectiles.iterator(); jterator.hasNext();) {
				Projectile projectile = jterator.next();
				if (bullet.collisionBox.intersects(projectile.collisionBox)) {
					projectile.isActive = false;
				}
			}
			for (Iterator<Asteroid> jterator = asteroids.iterator(); jterator.hasNext();) {
				Asteroid asteroid = jterator.next();
				if (bullet.collisionBox.intersects(asteroid.collisionBox)) {
					bullet.isActive = false;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!GamePanel.gamePaused) {
			if (arg0.getSource() == increaseSpeed) {
				speed += 1;
			}
			if (arg0.getSource() == alienSpawn) {
				addAlien();
			}
			if (arg0.getSource() == asteroidSpawn) {
				addAsteroid();
			}
			if (arg0.getSource() == powerupSpawn) {
				addPowerup();
			}
			if (arg0.getSource() == powerfulAlienSpawn) {
				addPowerfulAlien();
			}
			if (arg0.getSource() == powerfulAlienBulletSpawn) {
				for (Iterator<PowerfulAlien> iterator = powerfulAliens.iterator(); iterator.hasNext();) {
					powerfulAliensBullets.add(iterator.next().getBullet());
				}
			}
		}
	}
}
