package by.fxg.clp.event;

import by.fxg.clp.registry.merchant.Merchant;
import by.fxg.clp.registry.merchant.MerchantOffer;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.util.eventbus.Event;

public abstract class MerchantEvent extends Event {
	private final GameSession session;
	private final Merchant merchant;
	
	protected MerchantEvent(GameSession session, Merchant merchant) {
		this.session = session;
		this.merchant = merchant;
	}
	
	public GameSession getSession() {
		return this.session;
	}
	
	public Merchant getMerchant() {
		return this.merchant;
	}
	
	public static class MerchantOfferAcceptEvent extends MerchantEvent {
		private final MerchantOffer offer;
		
		public MerchantOfferAcceptEvent(GameSession session, Merchant merchant, MerchantOffer offer) {
			super(session, merchant);
			this.offer = offer;
		}
		
		public MerchantOffer getOffer() {
			return this.offer;
		}
	}
}
