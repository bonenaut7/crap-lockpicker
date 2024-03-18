package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.core.GameRegistry;
import by.fxg.clp.registry.items.Item;
import by.fxg.clp.registry.items.ItemInstance;

public class ItemInstanceSerializer extends Serializer<ItemInstance> {

	@Override
	public void write(Kryo kryo, Output output, ItemInstance object) {
		final Item item = object.getType();
		
		output.writeShort(item.id);
		output.writeInt(object.getSize());
	}

	@Override
	public ItemInstance read(Kryo kryo, Input input, Class<? extends ItemInstance> type) {
		final Item item = GameRegistry.findItemByID(input.readShort());
		if (item == null) {
			return null;
		}

		return new ItemInstance(item, input.readInt());
	}
}
