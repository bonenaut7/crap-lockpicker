package by.fxg.clp.registry;

import by.fxg.clp.util.IntRange;

public class LevelLayout {
	public final String locationName;
	public final float lockGenerationChance; // chance of lock generation
	public final IntRange difficulty; // min-max value of difficulty which this layout will represent
	public final IntRange containers; // min-max value of chests on the level
	public final IntRange doorLocks; // min-max value of door locks on the level
	
	public LevelLayout(String locationName, float lockGenerationChance, IntRange difficulty, IntRange containers, IntRange doorLocks) {
		this.locationName = locationName;
		this.lockGenerationChance = lockGenerationChance;
		this.difficulty = difficulty;
		this.containers = containers;
		this.doorLocks = doorLocks;
	}
}
