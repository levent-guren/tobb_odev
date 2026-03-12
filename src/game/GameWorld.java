package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import game.objects.GameObject;
import game.objects.SeedBank;
import game.objects.zombie.BasicZombie;
import game.objects.zombie.FastZombie;
import game.objects.zombie.GameZombie;
import game.objects.zombie.RunZombie;
import game.objects.zombie.TankZombie;
import game.plants.GamePlant;

public class GameWorld {
	private List<GamePlant> plants = new ArrayList<>();
	private List<GameObject> bullets = new ArrayList<>();
	private List<GameZombie> zombies = new ArrayList<>();
	private List<GameObject> other = new ArrayList<>();
	private Grid grid = new Grid();
	private long lastCreatedZombieTime;
	private long zombieCreateInterval = 10000;
	private boolean isPlaying;

	public GameWorld() {
		other.add(new SeedBank(10, 0));
		// ilk 5 saniye zombie üretilmesin
		lastCreatedZombieTime = System.currentTimeMillis() + 5000;
	}

	public SeedBank getSeedBank() {
		return (SeedBank) other.get(0);
	}

	public List<GameZombie> getZombies() {
		return zombies;
	}

	public List<GamePlant> getPlants() {
		return plants;
	}

	public void addPlant(GamePlant plant, Point gridCoord) {
		plants.add(plant);
		grid.setObject(plant, gridCoord);
	}

	public void addBullet(GameObject bullet) {
		bullets.add(bullet);
	}

	public void draw(Graphics g) {
		// arkadan öne sırası ile
		for (int i = 0; i < other.size(); i++) {
			other.get(i).draw(g);
		}
		for (int i = 0; i < plants.size(); i++) {
			plants.get(i).draw(g);
		}
		for (int i = 0; i < zombies.size(); i++) {
			zombies.get(i).draw(g);
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
	}

	public void tick() {
		for (int i = 0; i < other.size(); i++) {
			other.get(i).update();
		}
		for (int i = 0; i < plants.size(); i++) {
			plants.get(i).update();
		}
		for (int i = 0; i < zombies.size(); i++) {
			zombies.get(i).update();
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
		}
		// yaşamayan plant'leri sil
		plants.removeIf(p -> !p.isAlive());

		// yaşamayan zombie'leri sil
		zombies.removeIf(z -> !z.isAlive());

		// yaşamayan bullet'ları sil
		bullets.removeIf(b -> !b.isAlive());
		grid.clearDeadPlants();

		// zombi ekleme ya da zombie dalgası
		checkZombies();
	}

	private void checkZombies() {
		// belirli sürelerde rastgele zombie ekle
		if (System.currentTimeMillis() - lastCreatedZombieTime > zombieCreateInterval) {
			addNewZombie();
			lastCreatedZombieTime = System.currentTimeMillis();
		}
	}

	private void addNewZombie() {
		zombies.add(getRandomZombie());
	}

	private GameZombie getRandomZombie() {
		GameZombie newZombie;
		int type = (int) (Math.random() * 4); // zombie count
		switch (type) {
		case 0: {
			newZombie = new BasicZombie();
			break;
		}
		case 1: {
			newZombie = new FastZombie();
			break;
		}
		case 2: {
			newZombie = new RunZombie();
			break;
		}
		default:
			newZombie = new TankZombie();
		}
		int lane = (int) (Math.random() * 5); // lane count
		Rectangle box = grid.getWorldRect(9, lane);
		newZombie.setX(box.getX() + 150);
		newZombie.setY(box.getY());
		return newZombie;
	}

	public Grid getGrid() {
		return grid;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public List<GameObject> getBullets() {
		return bullets;
	}

	public void setBullets(List<GameObject> bullets) {
		this.bullets = bullets;
	}

	public List<GameObject> getOther() {
		return other;
	}

	public void setOther(List<GameObject> other) {
		this.other = other;
	}

	public long getLastCreatedZombieTime() {
		return lastCreatedZombieTime;
	}

	public void setLastCreatedZombieTime(long lastCreatedZombieTime) {
		this.lastCreatedZombieTime = lastCreatedZombieTime;
	}

	public void setPlants(List<GamePlant> plants) {
		this.plants = plants;
	}

	public void setZombies(List<GameZombie> zombies) {
		this.zombies = zombies;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

}
