package level_2_Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Car extends GameObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;

	public Car(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.speed = 15;
		if (needImage) {
			loadImage("car.png");
		}
	}

	public void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, width, height);
		}
	}

	public void up() {
		y -= speed;
	}

	public void down() {
		y += speed;
	}

	public void left() {
		x -= speed;
	}

	public void right() {
		x += speed;
	}

	public Bullet getBullet() {
		return new Bullet(x + width / 2, y + 35, 80, 20);
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
