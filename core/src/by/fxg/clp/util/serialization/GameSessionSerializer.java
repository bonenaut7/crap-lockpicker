package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.session.GameInventory;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.session.GameSettings;

public class GameSessionSerializer extends Serializer<GameSession> {
	@Override
	public void write(Kryo kryo, Output output, GameSession object) {
		kryo.writeObject(output, object.gameSettings);
		kryo.writeObject(output, object.gameInventory);
	}

	@Override
	public GameSession read(Kryo kryo, Input input, Class<? extends GameSession> type) {
		final GameSession session = new GameSession();
		session.gameSettings = kryo.readObject(input, GameSettings.class);
		session.gameInventory = kryo.readObject(input, GameInventory.class);
		
		return session;
	}
}
