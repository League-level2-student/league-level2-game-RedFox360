package level_2_Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Alien2 extends GameObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	
	public Alien2(int x, int y, int width, int height, int speed) {
		super(x, y, width, height);
		this.speed = speed;
		if (needImage) {
			loadImage("alien.png");
		}
	}

	public void update(Car c) {
		super.update();
		x += speed;
		if (x < c.x) {
			if (y > c.y) {
				y -= speed;
			} else if (y < c.y) {
				y += speed;
			} else if (x > c.x) {
				x -= speed;
			}
		}

	}

	public void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(Color.YELLOW);
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
