package by.fxg.clp.registry.merchant.locks;

import by.fxg.clp.render.canvas.lock.LockSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.util.IntRange;

public class LockOpened extends Lock {
	private static final IntRange DIFFICULTY_RANGE = IntRange.of(0, 3);
	
	@Override
	public IntRange getDifficultyRange() {
		return DIFFICULTY_RANGE;
	}

	@Override
	public LockState<LockOpened> createLockState() {
		return new LockState<LockOpened>(this, false);
	}
	
	@Override
	@Deprecated
	public boolean canOpenLock(GameSession session) {
		return false;
	}
	
	@Override
	@Deprecated
	public String getUnableToLockpickMessage() {
		return null;
	}

	@Override
	@Deprecated
	public LockSubCanvas getLockCanvas() {
		return null;
	}
}
