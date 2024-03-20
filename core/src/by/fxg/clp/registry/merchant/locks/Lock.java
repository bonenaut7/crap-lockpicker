package by.fxg.clp.registry.merchant.locks;

import by.fxg.clp.registry.GameRegistry;
import by.fxg.clp.render.canvas.lock.LockSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.util.IntRange;

public abstract class Lock {
	public abstract IntRange getDifficultyRange();
	
	public abstract LockState<?> createLockState();
	
	public boolean canBeUnlocked(GameSession session) {
		return true;
	}
	
	public boolean canBeUnlockedInstantly(GameSession session) {
		return session.gameInventory.hasItem(GameRegistry.toolLockpick);
	}
	
	// returns success or failure for unlocking process
	public boolean onInstantUnlock(GameSession session) {
		return session.gameInventory.removeItem(GameRegistry.toolLockpick);
	}
	
	public abstract LockSubCanvas getLockCanvas();
	
	public static Lock getRandomLock(int difficulty) {
		
	}
}
