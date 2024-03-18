package by.fxg.clp.registry.items;

import by.fxg.clp.util.ResourceTag;

public class ItemTradeable extends ItemTextured {
	private float price = -1;
	private int maxSaleStock = -1;
	private int maxPurchaseStock = -1;
	
	protected ItemTradeable(String itemName, String textureName, int maxInstanceSize) {
		super(itemName, textureName, maxInstanceSize);
	}
	
	/** Sale stock - stock of items that player could buy, Purchase stock - items purchaseable by MERCHANTS **/
	public Item setTradeable(float price, int maxSaleStock, int maxPurchaseStock, ResourceTag traderTag) {
		if (price < 0) throw new IllegalArgumentException("Price can't be less than 0");
		
		this.price = price;
		this.maxSaleStock = maxSaleStock;
		this.maxPurchaseStock = maxPurchaseStock;
		if (maxSaleStock > 0) {
			this.addResourceTag(ResourceTag.PURCHASEABLE_BY_MERCHANTS);
			
			if (traderTag != null) {
				this.addResourceTag(traderTag);
			}
		}
		return this;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public int getMaxSaleStock() {
		return this.maxSaleStock;
	}
	
	public int getMaxPurchaseStock() {
		return this.maxPurchaseStock;
	}
}
