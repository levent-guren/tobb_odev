package game.objects.seed;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import game.Assets;
import game.GameEngine;
import game.objects.SeedBank;
import game.plants.GamePlant;

public abstract class SeedObject implements Serializable {
	private static final long serialVersionUID = 7083750859086381212L;
	private transient SeedBank seedBank;
	private int sunValue;
	private int x;
	private int y;
	private Rectangle boundries;
	private transient BufferedImage image;
	private transient BufferedImage passiveImage;

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// dosyadan objeyi oku ve oluştur.
		in.defaultReadObject();
		// dosyaya yazılmayan seedBank değişkeninin değerini doldur.
		seedBank = GameEngine.getGameWorld().getSeedBank();
	}

	public SeedObject(BufferedImage image, int x, int y, int sunValue, SeedBank seedBank) {
		this.seedBank = seedBank;
		this.sunValue = sunValue;
		this.image = image;
		this.passiveImage = Assets.makeImagePassive(image, 0.3f);
		this.boundries = new Rectangle(x, y, image.getWidth(), image.getHeight());
		this.x = x;
		this.y = y;
	}

	protected SeedBank getSeedBank() {
		return seedBank;
	}

	public int getSunValue() {
		return sunValue;
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

	public Rectangle getBoundries() {
		return boundries;
	}

	public BufferedImage getImage() {
		return image;
	}

	public BufferedImage getPassiveImage() {
		return passiveImage;
	}

	// Objenin kendisini çizmesi sağlanır.
	public void draw(Graphics g) {
		if (getSeedBank().getSun() >= getSunValue()) {
			g.drawImage(image, getX(), getY(), image.getWidth(), image.getHeight(), null);
		} else {
			g.drawImage(passiveImage, getX(), getY(), passiveImage.getWidth(), passiveImage.getHeight(), null);
		}
	}

	public abstract GamePlant getObject();
}
