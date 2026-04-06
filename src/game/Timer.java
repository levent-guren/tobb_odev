package game;

import game.state.StatePlay;

public class Timer implements Runnable {
	private long gameTime = 0;
	private boolean stop;

	@Override
	public void run() {
		while (!stop) {
			if (GameEngine.getInstance().getState().getClass().equals(StatePlay.class)) {
				gameTime += 1;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}

		}
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public long getGameTime() {
		return gameTime;
	}

	public String getTime() {
		long time = gameTime;
		long dakika = time / 1000 / 60;
		time -= dakika * 1000 * 60;
		long saniye = time / 1000;
		time -= saniye * 1000;
		String ms = String.valueOf(time);
		if (ms.length() > 3) {
			ms = ms.substring(0, 3);
		}
		return dakika + ":" + saniye + "." + ms;
	}

}
