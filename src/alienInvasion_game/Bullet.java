package alienInvasion_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Bullet extends GameObject {
	public BufferedImage image;
	public boolean needImage = true;
	public boolean gotImage = false;
	public char direction;

	public Bullet(int x, int y, int width, int height, char direction) {
		super(x, y, width, height);
		this.speed = 10;
		this.direction = direction;
		if (needImage) {
			if (this.direction == 's') {
				loadImage("car_bullet.png");
			}
			else if (this.direction == 'u') {
				loadImage("up_bullet.png");
			}
			else if (this.direction == 'd') {
				loadImage("powerful_alien_bullet.png");
			}
		}
	}

	public void update() {
		super.update();
		if (direction == 's') {
			x -= speed;
		} else if (direction == 'u') {
			y -= speed;
		} else if (direction == 'd') {
			y += speed;
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
