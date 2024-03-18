package by.fxg.clp.session.merchant;

import by.fxg.clp.render.canvas.merchant.MerchantSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.session.items.ItemInstance;
import by.fxg.clp.session.items.ItemTradeable;

public class MerchantOfferPurchase extends MerchantOffer {
	private static final float PRICE_MODIFIER = 0.75f; // Price of item multiplies by this number to get purchase price
	private ItemTradeable sellableItem;
	private int stock;
	
	@Override
	public MerchantSubCanvas getOfferCanvas() {
		return;
	}
	
	@Override
	public boolean isOfferAvailable(GameSession session) {
		return
			this.stock > 0 &&									// Stock is more than 0
			this.sellableItem.getPrice() > 0 &&					// Price is more than 0
			session.gameInventory.hasItem(this.sellableItem); 	// Is there an item with required type in the inventory?
	}
	
	@Override
	public boolean onOfferAgreement(GameSession session) {
		if (!this.isOfferAvailable(session)) return false;
		
		if (session.gameInventory.removeItem(this.sellableItem)) {
			this.stock--;
			session.gameInventory.money += this.sellableItem.getPrice() * PRICE_MODIFIER;
			return true;
		}
		
		return false; // why it would ever happen?
	}
}
