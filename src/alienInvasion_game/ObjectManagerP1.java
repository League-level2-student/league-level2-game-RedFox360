package alienInvasion_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ObjectManagerP1 implements ActionListener {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	public static boolean spawnPowerfulAliens = true;
	public static boolean spawnAsteroids = true;
	public static boolean easterEggSpawned = false;
	public static boolean terminalSpawnAliens = true;
	public static boolean spawnPowerups = true;
	public static int score = 0;
	int speed = 1;
	int aliensWhoFellBackToEarth = 0;
	Rocketship rocketShip;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	ArrayList<TimerPowerup> powerups = new ArrayList<TimerPowerup>();
	ArrayList<PowerfulAlien> powerfulAliens = new ArrayList<PowerfulAlien>();
	ArrayList<PowerfulAlienBullet> powerfulAliensBullets = new ArrayList<PowerfulAlienBullet>();
	EasterEgg easterEgg;
	Random random = new Random();
	static int timeLeft = 50;
	public static Timer alienSpawn;
	public static Timer powerfulAlienSpawn;
	public static Timer powerfulAlienBulletSpawn;
	public static Timer increaseSpeed;
	public static Timer asteroidSpawn;
	public static Timer powerupSpawn;
	public static Timer timeLimit;
	public static Timer spawnEEgg;

	public ObjectManagerP1(Rocketship r) {
		rocketShip = r;
		if (needImage) {
			loadImage("space.png");
		}
		alienSpawn = new Timer(1750, this);
		asteroidSpawn = new Timer(6000, this);
		increaseSpeed = new Timer(20000, this);
		powerupSpawn = new Timer(15000, this);
		powerfulAlienSpawn = new Timer(3000, this);
		powerfulAlienBulletSpawn = new Timer(4000, this);
		spawnEEgg = new Timer(10000, this);
		timeLimit = new Timer(70000, this);
	}

	public void start() {
		timeLeft = 50;
		alienSpawn.start();
		asteroidSpawn.start();
		increaseSpeed.start();
		powerupSpawn.start();
		powerfulAlienSpawn.start();
		powerfulAlienBulletSpawn.start();
		timeLimit.start();
	}

	public void stop() {
		alienSpawn.stop();
		asteroidSpawn.stop();
		increaseSpeed.stop();
		powerupSpawn.stop();
		powerfulAlienSpawn.stop();
		powerfulAlienBulletSpawn.stop();
		timeLimit.stop();
	}

	void addAlien() {
		if (terminalSpawnAliens) {
			aliens.add(new Alien(random.nextInt(AlienInvasion.WIDTH), 0, 50, 50, speed));
		}
	}

	void addEasterEgg() {
		easterEgg = new EasterEgg(random.nextInt(AlienInvasion.WIDTH), random.nextInt(AlienInvasion.HEIGHT), 100, 100);
	}

	void addPowerfulAlien() {
		if (spawnPowerfulAliens && GamePanel.currentMode != GamePanel.EASY) {
			powerfulAliens.add(new PowerfulAlien(random.nextInt(AlienInvasion.WIDTH), 0, 50, 50, speed));
		}
	}

	void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	void addAsteroid() {
		if (spawnAsteroids) {
			asteroids.add(new Asteroid(random.nextInt(AlienInvasion.WIDTH), 0, 80, 80));
		}
	}

	void addPowerup() {
		if (spawnPowerups) {
			powerups.add(new TimerPowerup(random.nextInt(AlienInvasion.WIDTH), random.nextInt(AlienInvasion.HEIGHT),
					100, 100));
		}
	}

	public static int getTime() {
		return timeLeft;
	}

	public void update() {
		for (Alien alien : aliens) {
			alien.update();
			if (alien.y > AlienInvasion.HEIGHT) {
				alien.isActive = false;
				aliensWhoFellBackToEarth += 1;
				if (score != 0) {
					score -= 1;
				}
			}
		}
		for (Projectile projectile : projectiles) {
			projectile.update();
			if (projectile.y > AlienInvasion.HEIGHT) {
				projectile.isActive = false;
				score += 1;
			}
		}
		for (Asteroid asteroid : asteroids) {
			asteroid.update();
			if (asteroid.y > AlienInvasion.HEIGHT) {
				asteroid.isActive = false;
			}
		}
		for (TimerPowerup powerup : powerups) {
			powerup.update();
		}
		for (PowerfulAlien alien : powerfulAliens) {
			alien.update();
			if (alien.y > AlienInvasion.HEIGHT) {
				alien.isActive = false;
				aliensWhoFellBackToEarth += 1;
				if (score != 0) {
					score -= 1;
				}
			}
		}
		for (PowerfulAlienBullet bullet : powerfulAliensBullets) {
			bullet.update();
			if (bullet.y > AlienInvasion.HEIGHT) {
				bullet.isActive = false;
			}
		}
		if (easterEggSpawned) {
			easterEgg.update();
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
		if (easterEggSpawned) {
			easterEgg.draw(g);
		}
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
		for (Alien alien : aliens) {
			if (rocketShip.collisionBox.intersects(alien.collisionBox)) {
				rocketShip.isActive = false;
				alien.isActive = false;

			}
			for (Projectile projectile : projectiles) {
				if (projectile.collisionBox.intersects(alien.collisionBox)) {
					projectile.isActive = false;
					alien.isActive = false;
					score += 1;
				}
				for (Asteroid asteroid : asteroids) {
					if (projectile.collisionBox.intersects(asteroid.collisionBox)) {
						projectile.isActive = false;
					}
				}

			}
			for (Asteroid asteroid : asteroids) {
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
		for (TimerPowerup powerup : powerups) {
			if (powerup.collisionBox.intersects(rocketShip.collisionBox)) {
				powerup.isActive = false;
				GamePanel.halfTimer();
			}
		}
		for (PowerfulAlien alien : powerfulAliens) {
			if (alien.collisionBox.intersects(rocketShip.collisionBox)) {
				rocketShip.isActive = false;

				GamePanel.endText = "Your rocket was hit by a powerful alien.";
				GamePanel.currentState = GamePanel.END - 1;
			}
			for (Projectile projectile : projectiles) {
				if (alien.collisionBox.intersects(projectile.collisionBox)) {
					alien.isActive = false;
					projectile.isActive = false;
				}
			}
			for (Asteroid asteroid : asteroids) {
				if (alien.collisionBox.intersects(asteroid.collisionBox)) {
					alien.isActive = false;
				}
			}
		}
		for (PowerfulAlienBullet bullet : powerfulAliensBullets) {
			if (bullet.collisionBox.intersects(rocketShip.collisionBox)) {
				rocketShip.isActive = false;

			}
			for (Projectile projectile : projectiles) {
				if (bullet.collisionBox.intersects(projectile.collisionBox)) {
					projectile.isActive = false;
				}
			}
			for (Asteroid asteroid : asteroids) {
				if (bullet.collisionBox.intersects(asteroid.collisionBox)) {
					bullet.isActive = false;
				}
			}
		}
		if (easterEggSpawned) {
			if (easterEgg.collisionBox.intersects(rocketShip.collisionBox)) {

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
				timeLeft -= 1;
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
			if (arg0.getSource() == timeLimit) {
				JOptionPane.showMessageDialog(null, "Your rocket is failing...you are falling back to Earth...");
				rocketShip.isActive = false;
			}
			if (arg0.getSource() == spawnEEgg) {
				addEasterEgg();
				easterEggSpawned = true;
			}
		}
	}

	public static void playAudioFile(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}
}
