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
			stream.writeObject(world);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				ObjectInputStream stream = new ObjectInputStream(bis)) {
			GameEngine.getGameWorld().readFrom((GameWorld) stream.readObject());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}
}
