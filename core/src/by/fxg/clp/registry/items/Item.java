package by.fxg.clp.registry.items;

import java.util.EnumSet;

import com.badlogic.gdx.graphics.Texture;

import by.fxg.clp.core.Game;
import by.fxg.clp.core.ResourceManager;
import by.fxg.clp.registry.quests.Quest;
import by.fxg.clp.util.ResourceTag;

public abstract class Item {
	private static short nextID = 0;
	
	// Registry ID number for serialization purpose
	public final short id;
	
	// Item name :V
	protected String itemName;
	
	// Max size of item instance (like max item count in a single pile)
	protected int maxInstanceSize = 1;
	
	// Item texture that being registered on some load stage
	protected Texture itemTexture = null;
	
	// Resource tags
	private EnumSet<ResourceTag> resourceTags;
	
	protected Item(String itemName) {
		this.id = nextID++;
		this.itemName = itemName;
	}

	public void registerTexture(ResourceManager resourceManager) {
		this.itemTexture = resourceManager.getAssetWithFallback(Texture.class, this.getTexturePath(), resourceManager.getPlaceholderTexture());
	}
	
	// Tags
	
	public Item setResourceTags(ResourceTag resourceTag, ResourceTag... rest) {
		this.resourceTags = EnumSet.of(resourceTag, rest);
		return this;
	}
	
	public Item addResourceTag(ResourceTag resourceTag) {
		if (this.resourceTags == null) {
			this.resourceTags = EnumSet.of(resourceTag);
			return this;
		}
		
		if (!this.resourceTags.contains(resourceTag)) {
			this.resourceTags.add(resourceTag);
		}
		return this;
	}
	
	/** Used to compare items for inventory display **/
	public int getTagsWeight() {
		int tagsWeight = 0;
		
		for (ResourceTag tag : this.resourceTags) {
			if (tag != null) {
				tagsWeight += tag.getWeight();
			}
		}
		
		// unbelieveable shit happened below
		final Quest activeQuest = Game.getSession().activeQuest;
		if (activeQuest != null && activeQuest.getRequiredItem() == this) {
			tagsWeight += ResourceTag.QUEST_TARGET_ACTIVE_WEIGHT;
		}
		
		return tagsWeight;
	}

	// Utility functions
	
	public boolean hasTag(ResourceTag resourceTag) {
		return this.resourceTags.contains(resourceTag);
	}
	
	public boolean hasTags(EnumSet<ResourceTag> resourceTags) {
		return this.resourceTags.containsAll(resourceTags);
	}
	
	public EnumSet<ResourceTag> getTags() {
		return this.resourceTags;
	}
	
	public String getTexturePath() {
		return "items/undefined.png";
	}
	
	public String getItemName() {
		return this.itemName;
	}
	
	public int getMaxInstanceSize() {
		return this.maxInstanceSize;
	}
	
	public Texture getTexture() {
		return this.itemTexture;
	}
	
	public boolean isTypeEqual(Item item) {
		return this == item; // LOL
	}
}
