package by.fxg.clp.registry.merchant.locks;

import by.fxg.clp.registry.GameRegistry;
import by.fxg.clp.render.canvas.lock.LockSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.util.IntRange;

public class LockWrenchableQTE extends Lock {
	private static final IntRange DIFFICULTY_RANGE = IntRange.of(0, 2);
	
	@Override
	public IntRange getDifficultyRange() {
		return DIFFICULTY_RANGE;
	}

	@Override
	public LockState<LockWrenchableQTE> createLockState() {
		return new LockState<LockWrenchableQTE>(this);
	}
	
	@Override
	public boolean canBeUnlockedInstantly(GameSession session) {
		return false;
	}

	@Override
	public LockSubCanvas getLockCanvas() {
		return;
	}
}
