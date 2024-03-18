package by.fxg.clp.registry.merchant;

import by.fxg.clp.registry.items.ItemInstance;
import by.fxg.clp.registry.items.ItemTradeable;
import by.fxg.clp.render.canvas.merchant.MerchantSubCanvas;
import by.fxg.clp.session.GameSession;

public class MerchantOfferPurchase extends MerchantOffer {
	private static final float PRICE_MODIFIER = 0.75f; // Price of item multiplies by this number to get purchase price
	private ItemTradeable itemType;
	private int stock;
	
	public MerchantOfferPurchase(ItemTradeable itemType, int stock) {
		this.itemType = itemType;
		this.stock = stock;
	}
	
	@Override
	public MerchantSubCanvas getOfferCanvas() {
		return;
	}
	
	@Override
	public boolean isOfferAvailable(GameSession session) {
		return
			this.stock > 0 &&									// Stock is more than 0
			this.itemType.getPrice() > 0 &&					// Price is more than 0
			session.gameInventory.hasItem(this.itemType); 	// Is there an item with required type in the inventory?
	}
	
	@Override
	public boolean onOfferAgreement(GameSession session) {
		if (!this.isOfferAvailable(session)) return false;
		
		if (session.gameInventory.removeItem(this.itemType)) {
			this.stock--;
			session.gameInventory.money += this.itemType.getPrice() * PRICE_MODIFIER;
			return true;
		}
		
		return false; // why it would ever happen?
	}
	
	public ItemTradeable getOfferItem() {
		return this.itemType;
	}
	
	public int getStock() {
		return this.stock;
	}
}
