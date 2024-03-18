package by.fxg.clp.registry;

import java.util.function.Function;

import com.badlogic.gdx.utils.Array;

import by.fxg.clp.core.Game;
import by.fxg.clp.core.ResourceManager;
import by.fxg.clp.registry.items.Item;
import by.fxg.clp.registry.items.ItemResource;
import by.fxg.clp.registry.items.ItemTool;
import by.fxg.clp.util.IntRange;
import by.fxg.clp.util.ResourceTag;
import by.fxg.clp.util.StringBlob;

public class GameRegistry {
	private static Array<Item> registeredItems = new Array<>();
	public static StringBlob namesFirstnames;
	public static StringBlob namesSurnames;
	public static StringBlob dialogues;
	
	// Level layouts
	public static LevelLayout layoutTutorial;
	public static LevelLayout layoutRitualSite;
	
	// Merchants
	
	// Items
	public static ItemTool toolLockpick;
	public static ItemTool toolWrenches;
	public static ItemTool toolCrapbomb;
	
	public static ItemResource itemLungsBooster;
	public static ItemResource itemBananaBranch;
	public static ItemResource itemDoritos;
	public static ItemResource itemModem;
	public static ItemResource itemTVRemote;
	
	public static ItemResource itemGoldBars;
	
	public static ItemResource itemRitualMask;
	public static ItemResource itemCursedPainting;
	public static ItemResource itemDemonFruit;
	public static ItemResource itemDevilContract;
	
	public static void init() {
		final ResourceManager resourceManager = Game.INSTANCE.resourceManager;
		
		// String Blobs ===================================================================================================================================
		
		namesFirstnames = resourceManager.getAssetWithFallback(StringBlob.class, "stringblobs/firstnames.blob", StringBlob.PLACEHOLDER);
		namesSurnames = resourceManager.getAssetWithFallback(StringBlob.class, "stringblobs/surnames.blob", StringBlob.PLACEHOLDER);
		dialogues = resourceManager.getAssetWithFallback(StringBlob.class, "stringblobs/dialogues.blob", StringBlob.PLACEHOLDER);
		
		// Layouts ========================================================================================================================================
		
		layoutTutorial = new LevelLayout(IntRange.of(1, 3), IntRange.ZERO);
		
		//layoutRitualSite = new LevelLayout(IntRange.of(), IntRange.ZERO);
		
		// Items ==========================================================================================================================================
		
		toolLockpick = new ItemTool("Lockpick", "lockpick", 25);
		toolWrenches = new ItemTool("Wrenches", "wrenches", 1);
		toolCrapbomb = new ItemTool("Crap-bomb", "crapbomb", 1);
		
		// Common
		itemLungsBooster =	(ItemResource)new ItemResource("Lungs booster", "lungsbooster").setTradeable(4.99f, 0, 10, ResourceTag.COMMON_MERCHANDISE);
		itemBananaBranch = 	(ItemResource)new ItemResource("Banana branch", "bananabranch").setTradeable(10, 1, 5, ResourceTag.COMMON_MERCHANDISE);
		itemDoritos = 		(ItemResource)new ItemResource("Dorito$", "doritochips").setTradeable(20, 2, 2, ResourceTag.COMMON_MERCHANDISE);
		itemModem = 		(ItemResource)new ItemResource("ADSL Modem", "modem").setTradeable(75, 1, 3, ResourceTag.COMMON_MERCHANDISE);
		itemTVRemote = 		(ItemResource)new ItemResource("TV Remote", "tvremote").setTradeable(15, 1, 5, ResourceTag.COMMON_MERCHANDISE);
		
		// Covert
		
		// Ritual
		itemRitualMask =		(ItemResource)new ItemResource("Ritual Mask", "ritualmask").setTradeable(250, 0, 2, ResourceTag.RITUAL_MERCHANDISE);
		itemCursedPainting = 	(ItemResource)new ItemResource("Cursed Painting", "cursedpainting").setTradeable(350, 0, 1, ResourceTag.RITUAL_MERCHANDISE);
		itemDemonFruit =		(ItemResource)new ItemResource("Demonfruit", "demonfruit").setTradeable(75, 0, 5, ResourceTag.RITUAL_MERCHANDISE);
		itemDevilContract = 	(ItemResource)new ItemResource("Devil's Contract", "devilcontract").setTradeable(500, 0, 1, ResourceTag.RITUAL_MERCHANDISE);
		
		// (ItemResource)new ItemResource("name", "texture");
		loadItemsTextures(resourceManager);
	}
	
	// slow but there's no so much items
	public boolean registerItem(Item item) {
		if (item == null || item.getItemName() == null) {
			return false;
		}
		
		if (findItemByID(item.id) != null) {
			return false;
		}
		
		if (findItemByName(item.getItemName()) != null) {
			return false;
		}
		
		registeredItems.add(item);
		return true; // s u c c e s s
	}
	
	private static void loadItemsTextures(ResourceManager resourceManager) {
		for (int idx = 0; idx < registeredItems.size; idx++) {
			final Item item = registeredItems.get(idx);
			if (item != null) {
				item.registerTexture(resourceManager);
			}
		}
	}
	
	public static Item findItemByID(short id) {
		if (id < 0) return null;
		return findItemBy(item -> item.id == id);
	}
	
	public static Item findItemByName(String itemName) {
		if (itemName == null) return null;
		
		// it's reversed because itemName already can't be a null, but item#getItemName may return null :b
		return findItemBy(item -> itemName.equals(item.getItemName()));
	}
	
	public static Item findItemBy(Function<Item, Boolean> function) {
		if (function == null) return null;

		for (int i = 0; i < registeredItems.size; i++) {
			final Item item = registeredItems.get(i);
			if (item != null && function.apply(item)) {
				return item;
			}
		}
		
		return null;
	}
}
