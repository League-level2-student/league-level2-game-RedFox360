package alienInvasion_game;

import java.awt.Rectangle;

public abstract class GameObject {
	int x, y, width, height;
	int speed = 0;
	boolean isActive = true;
	Rectangle collisionBox;
	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		collisionBox = new Rectangle(x, y, width, height);
	}
	public void update() {
        collisionBox.setBounds(x, y, width, height);
	}
}