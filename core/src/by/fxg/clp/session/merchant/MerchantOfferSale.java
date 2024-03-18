package by.fxg.clp.session.merchant;

import by.fxg.clp.render.canvas.merchant.MerchantSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.session.items.ItemInstance;
import by.fxg.clp.session.items.ItemTradeable;

public class MerchantOfferSale extends MerchantOffer {
	private ItemTradeable sellableItem;
	private ItemInstance sellableItemInstance;
	private int stock;
	
	@Override
	public MerchantSubCanvas getOfferCanvas() {
		return;
	}
	
	@Override
	public boolean isOfferAvailable(GameSession session) {
		return
			this.stock > 0 &&												// Stock is more than 0
			this.sellableItem.getPrice() > 0 &&								// Price is more than 0
			session.gameInventory.money >= this.sellableItem.getPrice() &&	// Player has more money than cost of item
			session.gameInventory.addItem(this.sellableItemInstance, true); // Simulation pass of adding item to the inventory
	}
	
	@Override
	public boolean onOfferAgreement(GameSession session) {
		if (!this.isOfferAvailable(session)) return false;
		
		if (session.gameInventory.addItem(this.sellableItemInstance.clone())) {
			this.stock--;
			session.gameInventory.money -= this.sellableItem.getPrice();
			return true;
		}
		
		return false; // why it would ever happen?
	}
}
