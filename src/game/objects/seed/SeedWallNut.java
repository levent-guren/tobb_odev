package game.objects.seed;

import java.awt.image.BufferedImage;

import game.plants.GamePlant;
import game.plants.PlantWallNut;

public class SeedWallNut extends SeedObject {
	private static final long serialVersionUID = -4560421769910461398L;

	public SeedWallNut(BufferedImage image, int x, int y, int sunValue, SeedBank seedBank) {
		super(image, x, y, sunValue, seedBank);
	}

	@Override
	public GamePlant getObject() {
		return new PlantWallNut(this);
	}
}
