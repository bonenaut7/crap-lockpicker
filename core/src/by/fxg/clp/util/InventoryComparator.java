package by.fxg.clp.util;

import java.util.Comparator;

import by.fxg.clp.registry.items.Item;
import by.fxg.clp.registry.items.ItemInstance;

public class InventoryComparator implements Comparator<ItemInstance> {
	public static final InventoryComparator INSTANCE = new InventoryComparator();
	
	private InventoryComparator() {}
	
	@Override
	public int compare(ItemInstance first, ItemInstance second) {
		if (first == null) return second == null ? 0 : 1;
		if (second == null) return -1;
		
		final Item firstType = first.getType();
		final Item secondType = second.getType();
		
		if (firstType != secondType) {
			final int firstTags = firstType.getTagsWeight();
			final int secondTags = secondType.getTagsWeight();
			return firstTags > secondTags ? -1 : firstTags == secondTags ? 0 : 1;
		}
		return first.getSize() > second.getSize() ? -1 : first.getSize() == second.getSize() ? 0 : 1;
	}
}
