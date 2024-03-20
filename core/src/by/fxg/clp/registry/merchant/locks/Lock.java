package by.fxg.clp.registry.merchant.locks;

import by.fxg.clp.render.canvas.lock.LockSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.util.IntRange;

public abstract class Lock {
	public abstract IntRange getDifficultyRange();
	
	public abstract LockState<?> createLockState();
	
	public abstract boolean canOpenLock(GameSession session);
	
	public abstract String getUnableToLockpickMessage();
	
	public abstract LockSubCanvas getLockCanvas();
	
	public static Lock getRandomLock(int difficulty) {
		
	}
}
