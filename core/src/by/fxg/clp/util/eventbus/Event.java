package by.fxg.clp.util.eventbus;

public abstract class Event {
	private boolean isCancelled;
	
	public boolean isCancellable() {
		return false;
	}
	
	public boolean isCancelled() {
		return this.isCancellable() && this.isCancelled;
	}
	
	public boolean setCancelled(boolean isCancelled) {
		if (this.isCancellable()) {
			this.isCancelled = isCancelled;
			return true;
		}
		return false;
	}
}
