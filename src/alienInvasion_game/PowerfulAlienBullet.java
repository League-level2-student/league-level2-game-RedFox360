package alienInvasion_game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PowerfulAlienBullet extends GameObject {
	
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	
	public PowerfulAlienBullet(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.speed = 5;
		if (needImage) {
			loadImage("powerful_alien_bullet.png");
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
	
	public void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		}
	}
	
	public void update() {
		super.update();
		y += speed;
	}
	
}
