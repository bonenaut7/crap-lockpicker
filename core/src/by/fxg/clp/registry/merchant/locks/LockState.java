package by.fxg.clp.registry.merchant.locks;

public class LockState<T extends Lock> {
	private T lockObject;
	private boolean isLocked;
	
	public LockState(T lockObject) {
		this(lockObject, true);
	}
	
	public LockState(T lockObject, boolean isLocked) {
		this.lockObject = lockObject;
		this.isLocked = isLocked;
	}
	
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public boolean isLocked() {
		return this.isLocked;
	}
	
	public T getLock() {
		return this.lockObject;
	}
}
