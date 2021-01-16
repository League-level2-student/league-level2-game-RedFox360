package alienInvasion_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class AlienLeader {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	public AlienLeader() {
		if (needImage) {
		    loadImage ("alien_leader.png");
		}
	}
	public void draw(Graphics g, int x, int y, int width, int height) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(new Color(121, 52, 171));
			g.fillRect(x, y, width, height);
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

}
