package by.fxg.clp.registry.quests;

import com.badlogic.gdx.math.MathUtils;

import by.fxg.clp.registry.GameRegistry;
import by.fxg.clp.registry.LevelLayout;
import by.fxg.clp.registry.items.Item;
import by.fxg.clp.registry.items.ItemTradeable;
import by.fxg.clp.util.IntRange;

/** Basic structure for quests, pretty straightforward thing except
 *    for the questData which has serializable data in it like
 *    containers placement, locks state and etc **/
public final class Quest {
	public static final IntRange AVATARS_ID = IntRange.of(0, 19);
	public static final String TUTORIAL_DESCRIPTION = "You can always find me here, but don't forget to bring a Lungs Booster with you.";
	
	private boolean isTutorial = false; // Flag that means literally uncancellable permanent tutorial order
	
	private String ownerName;
	private int ownerIconID;
	private int descriptionID;
	private int difficulty;
	private Item requiredItem; // only one :(
	private int revenue; // (50 + rng(50)) * difficulty + item price?
	private LevelLayout levelLayout; // level layout that will be used with this quest
	
	// world data for this order, like chests placement, locks data, for active levels
	private QuestData questData;
	
	public static Quest createTutorialQuest() {
		final Quest quest = new Quest();
		quest.isTutorial = true;
		quest.ownerName = "Matvey \'bonenaut7\'";
		quest.ownerIconID = -1;
		quest.descriptionID = -1;
		quest.difficulty = 0; // Very Easy
		quest.requiredItem = GameRegistry.itemLungsBooster;
		quest.revenue = 25;  // was 50 in 0.1
		quest.levelLayout = GameRegistry.layoutTutorial;
		return quest;
	}
	
	public static Quest createRandomQuest() {
		final Quest quest = new Quest();
		quest.ownerName = String.format("%s %s", GameRegistry.namesFirstnames.random(), GameRegistry.namesSurnames.random());
		quest.ownerIconID = AVATARS_ID.random();
		quest.descriptionID = GameRegistry.dialogues.randomIndex();
		quest.difficulty = MathUtils.random(1, 3);
		quest.requiredItem = GameRegistry.LOOT_POOLS[quest.difficulty].random();
		quest.revenue = 50 + (25 * quest.difficulty);
		if (quest.requiredItem instanceof ItemTradeable) {
			quest.revenue += ((ItemTradeable)quest.requiredItem).getPrice() * 0.5f;
		}
		
		while (quest.levelLayout == null) {
			final LevelLayout layout = GameRegistry.LAYOUT_POOLS.random();
			if (layout.difficulty.isInRange(quest.difficulty)) {
				quest.levelLayout = layout;
			}
		}
		
		return quest;
	}
	
	public void onQuestAccepted() {
		
	}
	
	public Item getRequiredItem() {
		return this.requiredItem;
	}
	
	public QuestData getOrderData() {
		return this.questData;
	}
	
	public void setOrderData(QuestData questData) {
		this.questData = questData;
	}
}
