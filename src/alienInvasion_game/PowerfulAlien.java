package alienInvasion_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PowerfulAlien extends GameObject {

	int speed;
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;

	public PowerfulAlien(int x, int y, int width, int height, int speed) {
		super(x, y, width, height);
		this.speed = speed;
		if (needImage) {
			loadImage("powerful_alien.png");
		}
	}

	public void update() {
		super.update();
		y += speed;
	}

	public PowerfulAlienBullet getBullet() {
		return new PowerfulAlienBullet(x + width / 2, y + 35, 24, 90);
	}

	public void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(Color.PINK);
			g.drawRect(x, y, width, height);
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
