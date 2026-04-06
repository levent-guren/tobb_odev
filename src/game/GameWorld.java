package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import game.objects.GameObject;
import game.objects.freezer.Freezer;
import game.objects.seed.SeedBank;
import game.objects.zombie.BasicZombie;
import game.objects.zombie.FastZombie;
import game.objects.zombie.GameZombie;
import game.objects.zombie.RunZombie;
import game.objects.zombie.TankZombie;
import game.plants.GamePlant;
import game.state.StateWin;

public class GameWorld implements Serializable {
	private static final long serialVersionUID = 2680412234437533991L;
	private List<GamePlant> plants = new ArrayList<>();
	private List<GameObject> bullets = new ArrayList<>();
	private List<GameZombie> zombies = new ArrayList<>();
	private transient List<Freezer> freezers = new ArrayList<>();
	private transient List<GameObject> other = new ArrayList<>();
	private Grid grid = new Grid();
	private long lastCreatedZombieTime;
	private boolean isPlaying;
	private boolean zombieWave;
	private int zombieWaveCount;
	private int createdZombie;
	private long nextZombieCreateTime;
	private transient Timer2 timer2;
	private transient Timer timer;

	public GameWorld() {
		other.add(new SeedBank(10, 0));
		for (int i = 0; i < Constants.LANE_COUNT; i++) {
			Rectangle firstGrid = grid.getWorldRect(0, i);

			Freezer freezer = new Freezer(firstGrid.x - 50, firstGrid.y);
			freezers.add(freezer);
		}
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

	public List<Freezer> getFreezers() {
		return freezers;
	}

	public void setFreezers(List<Freezer> freezers) {
		this.freezers = freezers;
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
		for (int i = 0; i < freezers.size(); i++) {
			freezers.get(i).draw(g);
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
		for (int i = 0; i < freezers.size(); i++) {
			freezers.get(i).update();
		}
		// yaşamayan plant'leri sil
		plants.removeIf(p -> !p.isAlive());

		// yaşamayan zombie'leri sil
		zombies.removeIf(z -> !z.isAlive());

		// yaşamayan bullet'ları sil
		bullets.removeIf(b -> !b.isAlive());
		grid.clearDeadPlants();
		// oyun sonu testi
		if (getZombies().size() == 0 && getZombieWaveCount() >= Constants.MAX_WAVE_COUNT && !isZombieWave()) {
			GameEngine.getInstance().setState(new StateWin());
		} else {
			// zombi ekleme ya da zombie dalgası
			checkZombies();
		}
	}

	private void checkZombies() {
		// zombie dalgası zamanında isek burada zombie yaratmıyoruz. Zombileri
		// ZombieWave sınıfı yaratıyor.
		if (zombieWave) {
			return;
		}
		if (createdZombie >= Constants.ZOMBIE_WAVE_CREATE_ZOMBIE_COUNT) {
			// yeni dalga üret
			createdZombie = 0;
			zombieWave = true;
			new Thread(new ZombieWave()).start();
			zombieWaveCount++;
			return;
		}
		// zombie dalgası yok.
		// oyun bitti ise yeni zombie üretme
		if (zombieWaveCount >= Constants.MAX_WAVE_COUNT) {
			return;
		}
		// Rastgele süreler ile zombie üret
		if (System.currentTimeMillis() - lastCreatedZombieTime > nextZombieCreateTime) {
			addNewZombie();
			createdZombie++;
			lastCreatedZombieTime = System.currentTimeMillis();
			nextZombieCreateTime = (int) ((ThreadLocalRandom.current().nextDouble() * 5000) + 5000);
		}
	}

	public void addNewZombie() {
		zombies.add(getRandomZombie(false));
	}

	public GameZombie getRandomZombie(boolean wave) {
		GameZombie newZombie;
		int type = (int) (Math.random() * (wave ? Constants.ZOMBIE_COUNT : 2));
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
		lastCreatedZombieTime = System.currentTimeMillis() + Constants.ZOMBIE_CREATE_INITIAL_TIME;
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

	public int getCreatedZombie() {
		return createdZombie;
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
		this.nextZombieCreateTime = other.nextZombieCreateTime;
		this.isPlaying = other.isPlaying;
		this.zombieWave = false;
		this.zombieWaveCount = other.zombieWaveCount;
		this.createdZombie = other.createdZombie;
		if (other.zombieWave) {
			this.zombieWaveCount--;
			this.createdZombie = Constants.ZOMBIE_WAVE_CREATE_ZOMBIE_COUNT;
		}

	}

	public void waveOver() {
		zombieWave = false;
		createdZombie = 0;
	}

	public Timer getTimer() {
		return timer;
	}

	public Timer2 getTimer2() {
		return timer2;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void setTimer2(Timer2 timer2) {
		this.timer2 = timer2;
	}

}