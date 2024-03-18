package by.fxg.clp.util;

public class Timer {
	public final double updatesPerSecond;
	public final double millisPerUpdate;
	private double lastTimerUpdate;
	private double preUpdateTime;
	
	private int pendingUpdates;
	
	public Timer(double updatesPerSecond) {
		this.updatesPerSecond = updatesPerSecond;
		this.millisPerUpdate = 1000F / updatesPerSecond;
	}
	
	public void updateTimer() {
		final double millis = System.nanoTime() / 1000000.0D;
		
		final double elapsedSinceLastUpdate = millis - this.lastTimerUpdate;
		this.preUpdateTime += elapsedSinceLastUpdate / this.millisPerUpdate;
		
		this.pendingUpdates = (int)this.preUpdateTime;
		this.preUpdateTime -= this.pendingUpdates;
		
		this.lastTimerUpdate = millis;
	}
	
	public double getLastTimerUpdate() {
		return this.lastTimerUpdate;
	}
	
	public int getPendingUpdates() {
		return this.pendingUpdates;
	}
	
	public void resetUpdates() {
		this.pendingUpdates = 0;
	}
}