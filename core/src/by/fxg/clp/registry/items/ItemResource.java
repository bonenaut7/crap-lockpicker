package by.fxg.clp.registry.items;

import by.fxg.clp.util.ResourceTag;

public class ItemResource extends ItemTradeable {
	public ItemResource(String itemName, String textureName) {
		super(itemName, textureName, Short.MAX_VALUE); // don't ask me about maxInstanceSize...
		this.setResourceTags(ResourceTag.GENERATABLE, ResourceTag.QUEST_TARGET);
	}
}
