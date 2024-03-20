package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.items.Item;
import by.fxg.clp.registry.items.ItemTradeable;
import by.fxg.clp.registry.merchant.MerchantOfferSale;

public class MerchantOfferSaleSerializer extends Serializer<MerchantOfferSale> {
	@Override
	public void write(Kryo kryo, Output output, MerchantOfferSale object) {
		kryo.writeObject(output, object.getOfferItem());
		output.writeInt(object.getStock());
	}

	@Override
	public MerchantOfferSale read(Kryo kryo, Input input, Class<? extends MerchantOfferSale> type) {
		final Item item = kryo.readObject(input, Item.class);
		if (item instanceof ItemTradeable) {
			return new MerchantOfferSale((ItemTradeable)item, input.readInt());
		}
		
		return null;
	}
}
