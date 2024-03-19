package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.GameRegistry;
import by.fxg.clp.registry.items.Item;
import by.fxg.clp.registry.quests.Quest;
import by.fxg.clp.registry.quests.QuestData;

public class QuestSerializer extends Serializer<Quest> {
	@Override
	public void write(Kryo kryo, Output output, Quest object) {
		output.writeBoolean(object != null);
		if (object != null) {
			output.writeBoolean(object.isTutorialQuest());
			if (!object.isTutorialQuest()) {
				output.writeString(object.getOwnerName());
				output.writeInt(object.getOwnerIconID());
				output.writeInt(object.getDescriptionID());
				output.writeInt(object.getDifficulty());
				kryo.writeObject(output, object.getRequiredItem());
				output.writeInt(object.getRevenue());
				output.writeInt(GameRegistry.LEVEL_LAYOUTS.indexOf(object.getLevelLayout(), true));
			}
			kryo.writeObject(output, object.getQuestData());
		}
	}

	@Override
	public Quest read(Kryo kryo, Input input, Class<? extends Quest> type) {
		if (input.readBoolean()) {
			final boolean isTutorial = input.readBoolean();
			final Quest quest = isTutorial ? Quest.createTutorialQuest() : new Quest(
				input.readString(), input.readInt(), input.readInt(), input.readInt(),
				kryo.readObject(input, Item.class), input.readInt(),
				GameRegistry.getLevelLayoutByIndex(input.readInt())
			);
			quest.setQuestData(kryo.readObject(input, QuestData.class));
			return quest;
		}
		return null;
	}
}
