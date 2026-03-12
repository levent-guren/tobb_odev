package game.plants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import game.objects.GameObject;
import game.objects.seed.SeedObject;

public abstract class GamePlant extends GameObject {
	private static final long serialVersionUID = -221158884944965455L;
	private SeedObject creator;

	public GamePlant(BufferedImage image, int x, int y, int speed) {
		super(image, x, y, speed);
	}

	public GamePlant(BufferedImage image, SeedObject creator) {
		super(image);
		setHeight(80);
		setWidth(50);
		this.creator = creator;
	}

	public SeedObject getCreator() {
		return creator;
	}

	public void setCreator(SeedObject creator) {
		this.creator = creator;
	}

	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void draw(Graphics g) {
		drawShadow(g);
		g.drawImage(getImage(), (int) getX() + 5, (int) getY() + 10, getWidth() - 5, getHeight() - 20, null);
	}

	public void drawShadow(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int shadowWidth = 54;
		int shadowHeight = 22;
		int centerX = (int) getX() + 25;
		int centerY = (int) getY() + 68;

		AffineTransform oldTransform = g2d.getTransform();

		g2d.translate(centerX, centerY);
		g2d.scale(1.0, (double) shadowHeight / shadowWidth);

		// 2. Renk Yayılımı Ayarı (Fractions)
		float radius = shadowWidth / 2.0f;
		float[] dist = { 0.0f, 0.6f, 1.0f };

		// 3. Opaklık (Alpha) Ayarı
		int shadowAlpha = 190;
		Color[] colors = { new Color(0, 0, 0, shadowAlpha), // Merkez (Daha koyu)
				new Color(0, 0, 0, (int) (shadowAlpha * 0.5)), // Ara geçiş
				new Color(0, 0, 0, 0) // Dış kenar
		};

		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Float(0, 0), radius, dist, colors);

		g2d.setPaint(rgp);
		g2d.fillOval(-shadowWidth / 2, -shadowWidth / 2, shadowWidth, shadowWidth);

		g2d.setTransform(oldTransform);
	}
}
