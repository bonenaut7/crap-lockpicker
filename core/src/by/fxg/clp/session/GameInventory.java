package by.fxg.clp.session;

import java.util.EnumSet;
import java.util.function.Function;

import com.badlogic.gdx.utils.Array;

import by.fxg.clp.session.items.Item;
import by.fxg.clp.session.items.ItemInstance;
import by.fxg.clp.util.ResourceTag;

public class GameInventory {	
	public float money = 0;
	private Array<ItemInstance> items = new Array<>();
	
	// that's the most crappiest thing ever created
	public ItemInstance getItem(Function<ItemInstance, Boolean> query) {
		if (query == null) return null;
		
		for (int idx = 0; idx < this.items.size; idx++) {
			final ItemInstance itemInstance = this.items.get(idx);
			if (itemInstance != null && query.apply(itemInstance)) {
				return itemInstance;
			}
		}
		
		return null;
	}
	
	// that's the second crappiest thing ever created
	public Array<ItemInstance> getItems(Array<ItemInstance> outputArray, Function<ItemInstance, Boolean> query) {
		if (query == null) return null;
		
		for (int idx = 0; idx < this.items.size; idx++) {
			final ItemInstance itemInstance = this.items.get(idx);
			if (itemInstance != null && query.apply(itemInstance)) {
				outputArray.add(itemInstance);
			}
		}
		
		return outputArray;
	}
	
	public Array<ItemInstance> getItemsByType(Array<ItemInstance> outputArray, Item itemType) {
		if (outputArray == null || itemType == null) return outputArray;
		
		return this.getItems(outputArray, item -> item.getType() == itemType);
	}
	
	public Array<ItemInstance> getItemsByTags(Array<ItemInstance> outputArray, ResourceTag resourceTag, ResourceTag... other) {
		if (outputArray == null) return outputArray; // hmm... 
		
		final EnumSet<ResourceTag> resourceTags = other == null ? EnumSet.of(resourceTag) : EnumSet.of(resourceTag, other);
		return this.getItems(outputArray, item -> item.getType().hasTags(resourceTags));
	}
	
	public boolean addItem(ItemInstance inputInstance) {
		return this.addItem(inputInstance, false);
	}
	
	public boolean addItem(ItemInstance inputInstance, boolean simulate) {
		if (inputInstance == null) return false;
		
		final ItemInstance instance = !simulate ? inputInstance : inputInstance.clone();
		
		for (int idx = 0; idx < this.items.size; idx++) {
			final ItemInstance itemInstance = this.items.get(idx);
			if (itemInstance != null && itemInstance.combine(instance, simulate)) {
				if (instance.getSize() < 1) {
					break;
				}
			}
		}
		
		if (instance.getSize() > 0) {
			// If item has tag INV_SINGLE_INSTANCE but it's not simulation, then we should allow it to pass
			// because it will count as a force action, otherwise if it's simulation then we won't pass it
			if (simulate && instance.getType().hasTag(ResourceTag.INV_SINGLE_INSTANCE)) {
				return false;
			}
			
			this.items.add(instance);
		}
		
		// Simulation doesn't change inventory
		if (!simulate) {
			this.invalidate();
		}
		return true;
	}
	
	public boolean hasItem(Item itemType) {
		return this.hasItem(itemType, 1);
	}
	
	public boolean hasItem(Item itemType, int sizeThreshold) {
		if (itemType == null) return false;
		
		int thresholdCounter = sizeThreshold;
		for (int idx = 0; idx < this.items.size; idx++) {
			final ItemInstance itemInstance = this.items.get(idx);
			if (itemInstance != null && itemInstance.getType() == itemType) {
				thresholdCounter -= itemInstance.getSize();
				if (thresholdCounter < 1) {
					break;
				}
			}
		}
		
		return thresholdCounter < 1;
	}
	
	/** Removes single item from inventory with specified type **/
	public boolean removeItem(Item itemType) {
		if (itemType == null) return false;
		
		for (int idx = 0; idx < this.items.size; idx++) {
			final ItemInstance itemInstance = this.items.get(idx);
			if (itemInstance != null && itemInstance.getType() == itemType) {
				if (itemInstance.getSize() < 1) continue; // skipping if size is less than 1
				itemInstance.setSize(itemInstance.getSize() - 1);
				
				if (itemInstance.getSize() < 1) { // removing item from an array if it's invalid
					this.items.removeValue(itemInstance, true);
				}
				return true;
			}
		}
		
		return false;
	}
	
	/** Removes invalid(nulls, size 0) items that possibly could appear in inventory
	 *  Not sure how it will be possible, but this is just safety thing **/
	public void invalidate() {
		for (int idx = 0; idx < this.items.size; idx++) {
			final ItemInstance itemInstance = this.items.get(idx);
			if (itemInstance == null || !itemInstance.isValid()) {
				this.items.removeIndex(idx--);
			}
		}
	}
	
	public Array<ItemInstance> getItems() {
		return this.items;
	}
}
