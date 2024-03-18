package by.fxg.clp.util;

public enum ResourceTag {
	/** Only one instance of the item could be hold in the inventory (like tools) **/
	INV_SINGLE_INSTANCE,
	
	/** Item that could be used for quest targets **/
	QUEST_TARGET,
	
	/** Items that could be generated as a loot **/
	GENERATABLE,
	
	/** Items that could be used as a tools, like lockpicks **/
	TOOLS(100),
	
	/** Items that has this tag are could be sold to a merchants **/
	PURCHASEABLE_BY_MERCHANTS,
	
	RITUAL_MERCHANDISE(50),
	
	/** Items that could be sold to a huckster, if he needs it **/
	COVERT_MERCHANDISE(25),
	
	/** Items that could be sold to a cashier, if he needs it **/
	COMMON_MERCHANDISE,
	
	;
	
	public static final int QUEST_TARGET_ACTIVE_WEIGHT = 75;
	private final int tagWeight;
	
	ResourceTag() {
		this(0);
	}
	
	ResourceTag(int tagWeight) {
		this.tagWeight = tagWeight;
	}
	
	public int getWeight() {
		return this.tagWeight;
	}
}
