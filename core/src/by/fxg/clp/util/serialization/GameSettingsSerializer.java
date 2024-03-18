package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.session.GameSettings;

public class GameSettingsSerializer extends Serializer<GameSettings> {
	@Override
	public void write(Kryo kryo, Output output, GameSettings object) {
		output.writeShort(GameSettings.VERSION); // current version
		
		// sound levels, since 01
		output.writeFloat(object.masterSoundLevel);
		output.writeFloat(object.musicSoundLevel);
		output.writeFloat(object.sfxSoundLevel);
		
		// mouse controls, since 01
		output.writeFloat(object.mouseSensetivityX);
		output.writeFloat(object.mouseSensetivityY);
		output.writeBoolean(object.mouseInvertAxisY);
	}

	@Override
	public GameSettings read(Kryo kryo, Input input, Class<? extends GameSettings> type) {
		final short version = input.readShort();
		GameSettings settings = new GameSettings();
	
		// sound levels, since 01
		if (version >= 01) {
			settings.masterSoundLevel = input.readFloat();
			settings.musicSoundLevel = input.readFloat();
			settings.sfxSoundLevel = input.readFloat();
		}
	
		// mouse controls, since 01
		if (version >= 01) {
			settings.mouseSensetivityX = input.readFloat();
			settings.mouseSensetivityY = input.readFloat();
			settings.mouseInvertAxisY = input.readBoolean();
		}
		
		return settings;
	}
}
