package game.plants;

import java.io.IOException;
import java.io.ObjectInputStream;

import game.Assets;
import game.objects.seed.SeedObject;

public class PlantWallNut extends GamePlant {
	private static final long serialVersionUID = -2127016513965679178L;

	public PlantWallNut(SeedObject creator) {
		super(Assets.plantWallNut, creator);
		setHealth(300);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.plantWallNut);
	}
}
