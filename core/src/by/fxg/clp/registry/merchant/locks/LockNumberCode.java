package by.fxg.clp.registry.merchant.locks;

import by.fxg.clp.render.canvas.lock.LockSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.util.IntRange;

public class LockNumberCode extends Lock {
	private static final IntRange DIFFICULTY_RANGE = IntRange.of(2, 3);
	
	@Override
	public IntRange getDifficultyRange() {
		return DIFFICULTY_RANGE;
	}

	@Override
	public LockState<LockNumberCode> createLockState() {
		return new LockState<LockNumberCode>(this);
	}

	@Override
	public LockSubCanvas getLockCanvas() {
		return;
	}
}
