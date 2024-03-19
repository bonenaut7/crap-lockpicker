package by.fxg.clp.registry.merchant;

import com.badlogic.gdx.math.MathUtils;

import by.fxg.clp.registry.GameRegistry;
import by.fxg.clp.registry.items.ItemTradeable;
import by.fxg.clp.util.IntRange;

public class MerchantCashier extends Merchant {
	private static final IntRange OFFERS_RANGE = IntRange.of(2, 5);
	
	public MerchantCashier() {
		super("Cashier", "cashier", 1.0F, 2.0F);
	}

	@Override
	public void refreshOffers() {
		this.offers.size = 0;
		final int offers = OFFERS_RANGE.random();
		
		for (int saleOffers = 0, purchaseOffers = 0, idx = 0; idx < offers; idx++) {
			if (MathUtils.randomBoolean(this.getBalancedRandom(saleOffers, purchaseOffers))) {
				saleOffers++;
				
				final ItemTradeable item = this.getRandomItemFromPool();
				this.offers.add(new MerchantOfferSale(item, MathUtils.random(1, item.getMaxSaleStock())));
			} else {
				purchaseOffers++;
				
				final ItemTradeable item = this.getRandomItemFromPool();
				this.offers.add(new MerchantOfferPurchase(item, MathUtils.random(1, item.getMaxSaleStock())));
			}
		}
	}
	
	private ItemTradeable getRandomItemFromPool() {
		ItemTradeable item = null;
		while (item == null) {
			final ItemTradeable randomItem = GameRegistry.MERCHANT_CASHIER_TRADE_POOL.random();
			if (randomItem != null && randomItem.getMaxSaleStock() > 0) {
				item = randomItem;
				break;
			}
		}
		return item;
	}
}
