package game;

public class Timer2 implements Runnable {
	private long startTime = 0;
	private long currentTime = 0;
	private long pauseTime = 0;
	private boolean stop = false;

	public Timer2() {
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		while (!stop) {
			currentTime = System.currentTimeMillis();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
	}

	public long getTime() {
		if (pauseTime != 0) {
			return pauseTime - startTime;
		}
		return currentTime - startTime;
	}

	public void pause() {
		pauseTime = System.currentTimeMillis();
	}

	public void cont() {
		startTime += System.currentTimeMillis() - pauseTime;
		pauseTime = 0;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
