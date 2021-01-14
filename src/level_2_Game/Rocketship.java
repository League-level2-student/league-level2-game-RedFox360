package level_2_Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Rocketship extends GameObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;

	public Rocketship(int x, int y, int width, int height, int speed) {
		super(x, y, width, height);
		this.speed = speed;
		if (needImage) {
			loadImage("rocket.png");
		}
	}

	public void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
		}
	}

	public void up() {
		this.y -= speed;
	}

	public void down() {
		this.y += speed;
	}

	public void left() {
		x -= speed;
	}

	public void right() {
		x += speed;
	}

	public Projectile getProjectile() {
		return new Projectile(x + width / 2, y, 30, 30);
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
