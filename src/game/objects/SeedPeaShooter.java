package game.objects;

import java.awt.image.BufferedImage;

import game.objects.seed.SeedObject;
import game.plants.GamePlant;
import game.plants.PlantPeaShooter;

public class SeedPeaShooter extends SeedObject {
	private static final long serialVersionUID = 6743013996807936151L;

	public SeedPeaShooter(BufferedImage image, int x, int y, int sunValue, SeedBank seedBank) {
		super(image, x, y, sunValue, seedBank);
	}

	@Override
	public GamePlant getObject() {
		return new PlantPeaShooter(this);
	}
}
