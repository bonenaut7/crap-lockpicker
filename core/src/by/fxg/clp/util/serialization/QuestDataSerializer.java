package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.quests.QuestData;

public class QuestDataSerializer extends Serializer<QuestData> {
	@Override
	public void write(Kryo kryo, Output output, QuestData object) {
		output.writeBoolean(object != null);
		if (object != null) {
			output.writeBoolean(object.requiredItemFound);
		}
	}

	@Override
	public QuestData read(Kryo kryo, Input input, Class<? extends QuestData> type) {
		if (input.readBoolean()) {
			QuestData questData = new QuestData();
			questData.requiredItemFound = input.readBoolean();
			
			return questData;
		}
		return null;
	}
}
