package game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileOperations {
	private File file = new File(Constants.SAVE_FILE_NAME);

	public void save() {
		GameWorld world = GameEngine.getGameWorld();
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				ObjectOutputStream stream = new ObjectOutputStream(bos)) {
			// zaman yerine farklar yazılıyor.
			world.setLastCreatedZombieTime(System.currentTimeMillis() - world.getLastCreatedZombieTime());
			world.setLastCreatedZombieWave(System.currentTimeMillis() - world.getLastCreatedZombieWave());
			stream.writeObject(world);
			// zamanlar düzeltiliyor
			world.setLastCreatedZombieTime(System.currentTimeMillis() - world.getLastCreatedZombieTime());
			world.setLastCreatedZombieWave(System.currentTimeMillis() - world.getLastCreatedZombieWave());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		GameWorld world = GameEngine.getGameWorld();
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				ObjectInputStream stream = new ObjectInputStream(bis)) {
			GameEngine.getGameWorld().readFrom((GameWorld) stream.readObject());
			// okunan farklar zamana çevriliyor
			world.setLastCreatedZombieTime(System.currentTimeMillis() - world.getLastCreatedZombieTime());
			world.setLastCreatedZombieWave(System.currentTimeMillis() - world.getLastCreatedZombieWave());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}
}
