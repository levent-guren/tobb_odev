package game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import game.objects.GameObject;
import game.objects.zombie.GameZombie;
import game.plants.GamePlant;

public class FileOperations {
	private File file = new File("save.dat");

	public void save() {
		GameWorld world = GameEngine.getGameWorld();
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				ObjectOutputStream stream = new ObjectOutputStream(bos)) {
			stream.writeObject(world.getPlants());
			stream.writeObject(world.getBullets());
			stream.writeObject(world.getZombies());
			stream.writeObject(world.getGrid().getGrid());
			stream.writeLong(world.getLastCreatedZombieTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void load() {
		GameWorld world = GameEngine.getGameWorld();
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				ObjectInputStream stream = new ObjectInputStream(bis)) {
			world.setPlants((List<GamePlant>) stream.readObject());
			world.setBullets((List<GameObject>) stream.readObject());
			world.setZombies((List<GameZombie>) stream.readObject());
			world.getGrid().setGrid((GamePlant[][]) stream.readObject());
			world.setLastCreatedZombieTime(stream.readLong());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}
}
