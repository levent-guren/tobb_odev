package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import game.objects.GameObject;
import game.objects.seed.SeedBank;
import game.objects.zombie.BasicZombie;
import game.objects.zombie.FastZombie;
import game.objects.zombie.GameZombie;
import game.objects.zombie.RunZombie;
import game.objects.zombie.TankZombie;
import game.plants.GamePlant;

public class GameWorld implements Serializable {
	private static final long serialVersionUID = 2680412234437533990L;
	private List<GamePlant> plants = new ArrayList<>();
	private List<GameObject> bullets = new ArrayList<>();
	private List<GameZombie> zombies = new ArrayList<>();
	private transient List<GameObject> other = new ArrayList<>();
	private Grid grid = new Grid();
	private long lastCreatedZombieTime;
	private long lastCreatedZombieWave;
	private boolean isPlaying;
	private boolean zombieWave;
	private int zombieWaveCount;
	private int createdZombieForWave;

	public GameWorld() {
		other.add(new SeedBank(10, 0));
		newGame();
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
		// zombie dalgası yok ve son zombie dalgası da geçmemişse
		if (zombieWaveCount < Constants.MAX_WAVE_COUNT
				&& System.currentTimeMillis() - lastCreatedZombieWave > Constants.ZOMBIE_WAVE_CREATE_INTERVAL) {
			zombieWave = true;
			zombieWaveCount++;
			createdZombieForWave = 0;
			lastCreatedZombieWave = System.currentTimeMillis();
			System.out.println("Wave:" + zombieWaveCount);
		}
		if (zombieWave) {
			// wave zamanı daha sık zombie üretilecek
			if (System.currentTimeMillis() - lastCreatedZombieTime > Constants.ZOMBIE_CREATE_INTERVAL_FOR_WAVE) {
				addNewZombie();
				createdZombieForWave++;
				if (createdZombieForWave > Constants.ZOMBIE_CREATE_COUNT_FOR_WAVE + 2 * zombieWaveCount) {
					// wave end
					zombieWave = false;
					lastCreatedZombieWave = System.currentTimeMillis();
					System.out.println("Wave end");
				}
				lastCreatedZombieTime = System.currentTimeMillis();
			}
		} else
		// belirli sürelerde rastgele zombie ekle
		// son zombie dalgasından sonra zombei üretme
		if (zombieWaveCount < Constants.MAX_WAVE_COUNT
				&& System.currentTimeMillis() - lastCreatedZombieTime > Constants.ZOMBIE_CREATE_INTERVAL) {
			addNewZombie();
			lastCreatedZombieTime = System.currentTimeMillis();
		}
	}

	private void addNewZombie() {
		zombies.add(getRandomZombie());
	}

	private GameZombie getRandomZombie() {
		GameZombie newZombie;
		int type = (int) (Math.random() * Constants.ZOMBIE_COUNT);
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
		int lane = (int) (Math.random() * Constants.LANE_COUNT); // lane count
		Rectangle box = grid.getWorldRect(Constants.GRID_COLUMN_COUNT, lane);
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

	public void start() {
		// ilk saniye zombie üretilmesin. Initial time kadar beklensin.
		lastCreatedZombieTime = System.currentTimeMillis() + Constants.ZOMBIE_CREATE_INITIAL_TIME
				- Constants.ZOMBIE_CREATE_INTERVAL;
		lastCreatedZombieWave = lastCreatedZombieTime;
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

	public long getLastCreatedZombieWave() {
		return lastCreatedZombieWave;
	}

	public void setLastCreatedZombieWave(long lastCreatedZombieWave) {
		this.lastCreatedZombieWave = lastCreatedZombieWave;
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

	public boolean isZombieWave() {
		return zombieWave;
	}

	public void setZombieWave(boolean zombieWave) {
		this.zombieWave = zombieWave;
	}

	public int getZombieWaveCount() {
		return zombieWaveCount;
	}

	public void setZombieWaveCount(int zombieWaveCount) {
		this.zombieWaveCount = zombieWaveCount;
	}

	public void newGame() {
		plants.clear();
		bullets.clear();
		zombies.clear();
		grid.clear();
		zombieWaveCount = 0;
		zombieWave = false;
		getSeedBank().setSun(Constants.START_SUN_VALUE);
	}

	public void readFrom(GameWorld other) {
		this.plants = other.plants;
		this.bullets = other.bullets;
		this.zombies = other.zombies;
		this.grid = other.grid;
		this.lastCreatedZombieTime = other.lastCreatedZombieTime;
		this.lastCreatedZombieWave = other.lastCreatedZombieWave;
		this.isPlaying = other.isPlaying;
		this.zombieWave = other.zombieWave;
		this.zombieWaveCount = other.zombieWaveCount;
	}

}
