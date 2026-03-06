package game.objects;

import java.awt.Graphics;

public abstract class GameObject {
	private int x;
	private int y;
	private boolean isAlive = true;

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	// zaman geçince çağırılır. Objenin kendisini güncellemesi sağlanır.
	public abstract void update();

	// Objenin kendisini çizmesi sağlanır.
	public abstract void draw(Graphics g);

}
