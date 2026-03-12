package game.objects.seed;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import game.Assets;
import game.GameEngine;
import game.objects.GameObject;

public class SeedBank extends GameObject {
	private static final long serialVersionUID = -5061531887116268851L;
	private List<SeedObject> seeds = new ArrayList<>();
	private Rectangle boundries = new Rectangle((int) getX(), (int) getY(), Assets.seedBank.getWidth(),
			Assets.seedBank.getHeight());
	private int sun;
	private int seedWidth = 70;
	private int seedsLeftMargin = 92;
	private SeedObject catchSeed;
	private int mouseX, mouseY;

	public SeedBank(int x, int y) {
		super(Assets.seedBank, x, y, 0);
		init();
	}

	private void init() {
		setWidth(Assets.seedBank.getWidth());
		setHeight(Assets.seedBank.getHeight());
		seeds.add(new SeedSunFlower(Assets.seedSunFlower, (int) getX() + seedsLeftMargin + seedWidth * 0, 8, 50, this));
		seeds.add(new SeedPeaShooter(Assets.seedPeaShooter, (int) getX() + seedsLeftMargin + seedWidth * 1, 8, 100,
				this));
		seeds.add(new SeedSnowPeaShooter(Assets.seedSnowPeaShooter, (int) getX() + seedsLeftMargin + seedWidth * 2, 8,
				175, this));
		seeds.add(new SeedWallNut(Assets.seedWallNut, (int) getX() + seedsLeftMargin + seedWidth * 3, 8, 50, this));
		seeds.add(new SeedCherryBomb(Assets.seedCherryBomb, (int) getX() + seedsLeftMargin + seedWidth * 4, 8, 150,
				this));
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);

		// sun
		g.setFont(Assets.fontBebas);
		String sunText = String.valueOf(sun);
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int textWidth = metrics.stringWidth(sunText);
		int x = (int) getX() + 40 - (textWidth / 2);
		int y = (int) getY() + 78;
		g.drawString(String.valueOf(sun), x, y);

		for (int i = 0; i < seeds.size(); i++) {
			seeds.get(i).draw(g);
		}
		if (catchSeed != null) {
			// hold seed
			g.drawImage(catchSeed.getImage(), mouseX - catchSeed.getPassiveImage().getWidth() / 2,
					mouseY - catchSeed.getPassiveImage().getHeight() / 2, catchSeed.getPassiveImage().getWidth(),
					catchSeed.getPassiveImage().getHeight(), null);
			// draw grid box
			Point gridPoint = GameEngine.getGameWorld().getGrid().getGridCoord(mouseX, mouseY);
			if (gridPoint != null) {
				// mouse is over the grid
				Rectangle r = GameEngine.getGameWorld().getGrid().getWorldRect(gridPoint.x, gridPoint.y);
				g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
			}
		}
	}

	public Rectangle getBoundries() {
		return boundries;
	}

	public void mousePressed(MouseEvent e) {
		for (SeedObject seed : seeds) {
			if (seed.getBoundries().contains(e.getX(), e.getY())) {
				if (sun >= seed.getSunValue()) {
					// clicked seed objects
					catchSeed = seed;
					mouseX = e.getX();
					mouseY = e.getY();
					break;
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (catchSeed != null) {
			Point gridPoint = GameEngine.getGameWorld().getGrid().getGridCoord(e.getX(), e.getY());
			if (gridPoint != null) {
				// released on grid
				if (GameEngine.getGameWorld().getGrid().getObject(gridPoint.x, gridPoint.y) == null) {
					// grid is empty
					GameEngine.getGameWorld().addPlant(this.catchSeed.getObject(), gridPoint);
					sun -= this.catchSeed.getSunValue();
				}
			}

		}
		this.catchSeed = null;
	}

	public int getSun() {
		return sun;
	}

	public void setSun(int sun) {
		this.sun = sun;
	}

	public void addSun(int value) {
		sun += value;
	}

	public void mouseDragged(MouseEvent e) {
		if (catchSeed != null) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}

}
