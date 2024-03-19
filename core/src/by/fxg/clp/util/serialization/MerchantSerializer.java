package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.merchant.Merchant;

public class MerchantSerializer extends Serializer<Merchant> {
	@Override
	public void write(Kryo kryo, Output output, Merchant object) {
		output.writeBoolean(object != null);
		if (object != null) {
			
		}
	}

	@Override
	public Merchant read(Kryo kryo, Input input, Class<? extends Merchant> type) {
		if (input.readBoolean()) {
			
		}
		return null;
	}
}
