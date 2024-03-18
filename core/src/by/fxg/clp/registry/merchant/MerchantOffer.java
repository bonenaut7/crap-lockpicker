package by.fxg.clp.registry.merchant;

import by.fxg.clp.render.canvas.merchant.MerchantSubCanvas;
import by.fxg.clp.session.GameSession;

public abstract class MerchantOffer {
	/** Part of the gui that actually sells or buys items **/
	public abstract MerchantSubCanvas getOfferCanvas();
	
	/** Returns true or false based on offer availability including session data **/
	public abstract boolean isOfferAvailable(GameSession session);

	/** Called when player pressed offer button to buy or sell item **/
	public abstract boolean onOfferAgreement(GameSession session);
}
