package by.fxg.clp.registry.items;

import com.badlogic.gdx.math.MathUtils;

public final class ItemInstance {
	private final Item itemObject;
	private int size;
	
	public ItemInstance(Item item) {
		this(item, 1);
	}
	
	public ItemInstance(Item item, int size) {
		this.itemObject = item;
		this.setSize(size);
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setSize(int size) {
		this.size = MathUtils.clamp(size, 0, this.itemObject.maxInstanceSize);
	}
	
	public boolean combine(ItemInstance instance) {
		return this.combine(instance, false);
	}
	
	public boolean combine(ItemInstance instance, boolean simulate) {
		if (!this.isTypeEqual(instance)) {
			return false;
		}
		
		if (this.size >= this.itemObject.maxInstanceSize) {
			return false;
		}
		
		// Simulation changes only input instance
		if (!simulate) {
			this.setSize(this.size + instance.size);
		}
		instance.setSize(this.size + instance.size - this.itemObject.maxInstanceSize);
		return true;
	}
	
	public Item getType() {
		return this.itemObject;
	}
	
	// Used for item invalidation in inventory to remove items :)
	public boolean isValid() {
		return this.itemObject != null && this.size > 0;
	}
	
	public boolean isTypeEqual(ItemInstance instance) {
		return instance != null && this.itemObject.isTypeEqual(instance.itemObject);
	}
	
	public ItemInstance clone() {
		return new ItemInstance(this.itemObject, this.size);
	}
}
