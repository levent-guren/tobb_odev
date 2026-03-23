package game;

import game.objects.zombie.GameZombie;

public class ZombieWave implements Runnable {
	private int createdZombieForWave;
	private long lastCreatedZombieTime;
	private boolean markedForKill;

	@Override
	public void run() {
		System.out.println("Wave started");
		while (!markedForKill) {
			if (System.currentTimeMillis() - lastCreatedZombieTime > Constants.ZOMBIE_CREATE_INTERVAL_FOR_WAVE) {
				// zombie üret
				zombieUret();
				createdZombieForWave++;
				if (createdZombieForWave >= Constants.ZOMBIE_CREATE_COUNT_FOR_WAVE
						+ GameEngine.getGameWorld().getZombieWaveCount() * 5) {
					markedForKill = true;
				}
				// son üretilen zombi zamanını sakla
				lastCreatedZombieTime = System.currentTimeMillis();
				bekle(Constants.ZOMBIE_CREATE_INTERVAL_FOR_WAVE);
			}
		}
		// wave bitti. Sonlandırılıyor.
		GameEngine.getGameWorld().waveOver();
		System.out.println("Wave ended");
	}

	private void bekle(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}

	private void zombieUret() {
		GameWorld world = GameEngine.getGameWorld();
		GameZombie zombie = world.getRandomZombie(true);
		double zombieHealth = zombie.getHealth() * world.getZombieWaveCount() * Constants.ZOMBIE_WAVE_HEALTH_FACTOR;
		zombie.setHealth(zombieHealth);
		world.getZombies().add(zombie);
	}
}
