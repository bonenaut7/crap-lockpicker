package by.fxg.clp.registry.merchant.locks;

import com.badlogic.gdx.math.MathUtils;

public class LockState<T extends Lock> {
	private T lockObject;
	private boolean isLocked;
	private long seed0, seed1;
	
	public LockState(T lockObject) {
		this(lockObject, true);
	}
	
	public LockState(T lockObject, boolean isLocked) {
		this(lockObject, isLocked, MathUtils.random.nextLong(), MathUtils.random.nextLong());
	}
	
	public LockState(T lockObject, boolean isLocked, long seed0, long seed1) {
		this.lockObject = lockObject;
		this.isLocked = isLocked;
		this.seed0 = seed0;
		this.seed1 = seed1;
	}
	
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public T getLock() {
		return this.lockObject;
	}
	
	public boolean isLocked() {
		return this.isLocked;
	}
	
	public long getSeed0() {
		return this.seed0;
	}
	
	public long getSeed1() {
		return this.seed1;
	}
}
