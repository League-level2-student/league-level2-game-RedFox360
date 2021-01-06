package level_2_Game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ObjectManagerP1 implements ActionListener {
	Rocketship r;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	Random random = new Random();
	public ObjectManagerP1(Rocketship rocket) {
		r = rocket;
	}
	
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	public void addAlien() {
		aliens.add(new Alien(random.nextInt(AlienInvasion.WIDTH),0,50,50));
	}
	
	public void update() {
		for (Iterator<Alien> iterator = aliens.iterator(); iterator.hasNext();) {
			Alien alien = iterator.next();
			alien.update();
			if (alien.y > AlienInvasion.HEIGHT) {
				alien.isActive = false;
			}
		}
		for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext();) {
			Projectile p = iterator.next();
			p.update();
			if (p.y < 0) {
				p.isActive = false;
			}
		}
		purgeObjects();
	}
	public void draw(Graphics g) { 
		r.draw(g);
		for (Iterator<Alien> iterator = aliens.iterator(); iterator.hasNext();) {
			iterator.next().draw(g);
		}
		for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext();) {
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
			Projectile p = projectiles.get(i);
			if (!p.isActive) {
				projectiles.remove(i);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		addAlien();
	}

}
