package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.merchant.Merchant;
import by.fxg.clp.registry.quests.Quest;
import by.fxg.clp.session.GameInventory;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.session.GameSettings;

public class GameSessionSerializer extends Serializer<GameSession> {
	@Override
	public void write(Kryo kryo, Output output, GameSession object) {
		output.writeInt(0);
		
		kryo.writeObject(output, object.gameSettings);
		kryo.writeObject(output, object.gameInventory);
		
		output.writeInt(object.availableQuests.size);
		for (int idx = 0; idx < object.availableQuests.size; idx++) {
			kryo.writeObject(output, object.availableQuests.get(idx));
		}
		
		final int activeQuestIndex = object.availableQuests.indexOf(object.activeQuest, true);
		if (activeQuestIndex > -1) {
			output.writeBoolean(true);
			output.writeInt(activeQuestIndex);
		} else {
			output.writeBoolean(false);
			kryo.writeObject(output, object.activeQuest);
		}
		
		output.writeBoolean(object.merchantBystander != null);
		if (object.merchantBystander != null) {
			kryo.writeObject(output, object.merchantBystander);
		}
		
		output.writeInt(object.completedQuests);
		output.writeInt(object.failedQuests);
		output.writeFloat(object.totalEarned);
	}

	@Override
	public GameSession read(Kryo kryo, Input input, Class<? extends GameSession> type) {
		final GameSession session = new GameSession();
		input.skip(Integer.BYTES);
		
		session.gameSettings = kryo.readObject(input, GameSettings.class);
		session.gameInventory = kryo.readObject(input, GameInventory.class);
		
		final int availableQuests = input.readInt();
		for (int idx = 0; idx < availableQuests; idx++) {
			session.availableQuests.add(kryo.readObject(input, Quest.class));
		}
		
		if (input.readBoolean()) {
			session.activeQuest = session.availableQuests.get(input.readInt());
		} else {
			session.activeQuest = kryo.readObject(input, Quest.class);
		}
		
		if (input.readBoolean()) {
			session.merchantBystander = kryo.readObject(input, Merchant.class);
		}
		
		session.completedQuests = input.readInt();
		session.failedQuests = input.readInt();
		session.totalEarned = input.readInt();
		return session;
	}
}
