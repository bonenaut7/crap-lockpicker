package by.fxg.clp.registry.items;

import by.fxg.clp.util.ResourceTag;

public class ItemTool extends ItemTradeable {
	public ItemTool(String itemName, String textureName, int maxInstanceSize) {
		super(itemName, textureName, maxInstanceSize);
		this.addResourceTag(ResourceTag.TOOLS);
		this.addResourceTag(ResourceTag.INV_SINGLE_INSTANCE);
	}
}
