package by.fxg.clp.util.serialization;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.items.ItemInstance;
import by.fxg.clp.session.GameInventory;

public class GameInventorySerializer extends Serializer<GameInventory> {

	@Override
	public void write(Kryo kryo, Output output, GameInventory object) {
		object.invalidate();
		final Array<ItemInstance> items = object.getItems();
		
		output.writeFloat(object.money);
		output.writeInt(items.size);
		for (int idx = 0; idx < items.size; idx++) {
			kryo.writeObject(output, items.get(idx));
		}
	}

	@Override
	public GameInventory read(Kryo kryo, Input input, Class<? extends GameInventory> type) {
		final GameInventory inventory = new GameInventory();
		inventory.money = input.readFloat();
		
		final int itemsSize = input.readInt();
		for (int idx = 0; idx < itemsSize; idx++) {
			final ItemInstance itemInstance = kryo.readObject(input, ItemInstance.class);
			if (itemInstance.isValid()) {
				inventory.addItem(itemInstance);
			}
		}
		
		return inventory;
	}
}
