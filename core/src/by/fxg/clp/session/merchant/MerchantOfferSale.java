package by.fxg.clp.session.merchant;

import by.fxg.clp.render.canvas.merchant.MerchantSubCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.session.items.ItemInstance;
import by.fxg.clp.session.items.ItemTradeable;

public class MerchantOfferSale extends MerchantOffer {
	private ItemTradeable itemType;
	private ItemInstance itemInstance;
	private int stock;
	
	public MerchantOfferSale(ItemTradeable itemType, int stock) {
		this.itemType = itemType;
		this.itemInstance = new ItemInstance(itemType);
		this.stock = stock;
	}
	
	@Override
	public MerchantSubCanvas getOfferCanvas() {
		return;
	}
	
	@Override
	public boolean isOfferAvailable(GameSession session) {
		return
			this.stock > 0 &&												// Stock is more than 0
			this.itemType.getPrice() > 0 &&								// Price is more than 0
			session.gameInventory.money >= this.itemType.getPrice() &&	// Player has more money than cost of item
			session.gameInventory.addItem(this.itemInstance, true); // Simulation pass of adding item to the inventory
	}
	
	@Override
	public boolean onOfferAgreement(GameSession session) {
		if (!this.isOfferAvailable(session)) return false;
		
		if (session.gameInventory.addItem(this.itemInstance.clone())) {
			this.stock--;
			session.gameInventory.money -= this.itemType.getPrice();
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
