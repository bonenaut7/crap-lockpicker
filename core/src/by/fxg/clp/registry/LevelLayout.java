package by.fxg.clp.registry;

import by.fxg.clp.util.IntRange;

public class LevelLayout {
	public final IntRange containers; // min-max value of chests on the level
	public final IntRange doorLocks; // min-max value of door locks on the level
	
	public LevelLayout(IntRange containers, IntRange doorLocks) {
		this.containers = containers;
		this.doorLocks = doorLocks;
	}
}
