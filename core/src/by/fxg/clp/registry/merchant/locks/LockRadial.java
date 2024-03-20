package by.fxg.clp.registry.merchant.locks;

import by.fxg.clp.render.canvas.lock.LockSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.util.IntRange;

public class LockRadial extends Lock {
	private static final IntRange DIFFICULTY_RANGE = IntRange.of(1, 3);
	
	@Override
	public IntRange getDifficultyRange() {
		return DIFFICULTY_RANGE;
	}

	@Override
	public LockState<LockRadial> createLockState() {
		return new LockState<LockRadial>(this);
	}

	@Override
	public LockSubCanvas getLockCanvas() {
		return;
	}
}
