package game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
	private static final long serialVersionUID = -2790604115019319661L;

	// transient ile serialization işleminde (dosyaya kaydederken) yazılmasın
	// yapıyoruz
	private transient BufferedImage image;
	private double x;
	private double y;
	private int width;
	private int height;
	private double speed;
	private boolean isAlive = true;
	private double health;
	private double damage;

	public GameObject(BufferedImage image) {
		this.image = image;
		setWidth(70);
		setHeight(90);
	}

	public GameObject(BufferedImage image, double x, double y, double speed) {
		this(image);
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public void doDamage(double damage) {
		health -= damage;
		if (health <= 0) {
			setAlive(false);
		}
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public BufferedImage getImage() {
		return image;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Rectangle getBoundry() {
		return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
	}

	public void update() {
		setX(getX() + getSpeed());
	}

	public void draw(Graphics g) {
		g.drawImage(image, (int) getX(), (int) getY(), width, height, null);
	}

}
