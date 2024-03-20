package by.fxg.clp.util.serialization;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.GameRegistry;
import by.fxg.clp.registry.merchant.Merchant;
import by.fxg.clp.registry.merchant.MerchantOffer;

public class MerchantSerializer extends Serializer<Merchant> {
	@Override
	public void write(Kryo kryo, Output output, Merchant object) {
		final int merchantIndex = GameRegistry.REGISTERED_MERCHANTS.indexOf(object, true);
		output.writeInt(merchantIndex);
		
		// because merchants are working like items, all we should save are offers
		if (merchantIndex > -1) { 
			final Array<MerchantOffer> offers = object.getOffers();
			output.writeInt(offers.size);
			for (int idx = 0; idx < offers.size; idx++) {
				kryo.writeClassAndObject(output, offers.get(idx));
			}
		}
	}

	@Override
	public Merchant read(Kryo kryo, Input input, Class<? extends Merchant> type) {
		final int merchantIndex = input.readInt();
		Merchant merchant = null;
		if (merchantIndex > -1 && merchantIndex < GameRegistry.REGISTERED_MERCHANTS.size) {
			merchant = GameRegistry.REGISTERED_MERCHANTS.get(merchantIndex);
		}
		
		if (merchant != null) {
			final Array<MerchantOffer> offers = merchant.getOffers();
			offers.clear();
			
			final int totalOffers = input.readInt();
			for (int idx = 0; idx < totalOffers; idx++) {
				final Object object = kryo.readClassAndObject(input);
				if (object instanceof MerchantOffer) {
					offers.add((MerchantOffer)object);
				}
			}
			
			return merchant;
		}
		return null;
	}
}
