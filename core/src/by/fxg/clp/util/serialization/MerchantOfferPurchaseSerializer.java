package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.items.Item;
import by.fxg.clp.registry.items.ItemTradeable;
import by.fxg.clp.registry.merchant.MerchantOfferPurchase;

public class MerchantOfferPurchaseSerializer extends Serializer<MerchantOfferPurchase> {
	@Override
	public void write(Kryo kryo, Output output, MerchantOfferPurchase object) {
		kryo.writeObject(output, object.getOfferItem());
		output.writeInt(object.getStock());
	}

	@Override
	public MerchantOfferPurchase read(Kryo kryo, Input input, Class<? extends MerchantOfferPurchase> type) {
		final Item item = kryo.readObject(input, Item.class);
		if (item instanceof ItemTradeable) {
			return new MerchantOfferPurchase((ItemTradeable)item, input.readInt());
		}
		
		return null;
	}
}
