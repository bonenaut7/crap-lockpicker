package by.fxg.clp.registry.quests;

import com.badlogic.gdx.math.RandomXS128;

import by.fxg.clp.registry.items.Item;

public final class Quest {
	private boolean isSpecial = false; // Flag that means literally uncancellable permanent tutorial order
	
	public final long questSeed0, questSeed1; // seeds that are required to generate that quest
	private String ownerName;
	private int ownerIcon;
	private Item requiredItem; // only one :(
	private int revenue; // (50 + rng(50)) * difficulty + item price?
	
	// world data for this order, like chests placement, locks data, for active levels
	private QuestData orderData;
	
	// Generate tutorial order
	public Quest(long seed0, long seed1) {
		this.questSeed0 = seed0;
		this.questSeed1 = seed1;
	}
	
	// Generate randomly generated order
	public Quest(RandomXS128 random) {
		this(random, random.getState(0), random.getState(1));
	}
	
	// Generate randomly generated order with specified seed
	public Quest(RandomXS128 random, long seed0, long seed1) {
		random.setState(seed0, seed1);
		this.isSpecial = false;
		this.questSeed0 = seed0;
		this.questSeed1 = seed1;
		
		// FIXME complete
	}
	
	public static Quest createTutorialQuest() {
		final Quest quest = new Quest(0, 0);
		quest.isSpecial = true;
		quest.ownerName = "Matvey \'bonenaut7\'";
		// FIXME set icon
		
		quest.revenue = 25; // was 50 in 0.1
	}
	
	public Item getRequiredItem() {
		return this.requiredItem;
	}
}
