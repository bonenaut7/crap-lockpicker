package by.fxg.clp.session;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.core.Game;
import by.fxg.clp.registry.GameRegistry;
import by.fxg.clp.registry.merchant.Merchant;
import by.fxg.clp.registry.quests.Quest;

public class GameSession {
	public static final int MAX_GENERATED_QUESTS = 4;
	public GameSettings gameSettings;
	public GameInventory gameInventory;
	
	public Array<Quest> availableQuests = new Array<>();
	public Quest activeQuest = null;
	public Merchant merchantCashier;
	public Merchant merchantBystander;
	
	// Stats
	public int completedQuests = 0;
	public int failedQuests = 0;
	public float totalEarned = 0;
	
	public GameSession() {
		this.merchantCashier = GameRegistry.merchantCashier;
	}
	
	public void init() {
		this.gameSettings = new GameSettings();
		this.gameInventory = new GameInventory();
		this.activeQuest = null;
		this.merchantBystander = null;
	}
	
	public void save() {
		saveGameSession(Game.INSTANCE.resourceManager.kryo, this);
	}
	
	public void refillQuests() {
		for (int i = this.availableQuests.size - 1; i < MAX_GENERATED_QUESTS; i++) {
			this.availableQuests.add(Quest.createRandomQuest());
		}
	}
	
	
	
	
	
	// Static
	
	public static GameSession loadGameSession(Kryo kryo) {
		final FileHandle fileHandle = getSaveFileHandle();
		if (!fileHandle.exists()) return createNewSession(kryo);
		
		final Input input = new Input(fileHandle.read());
		GameSession session = kryo.readObject(input, GameSession.class);
		input.close();
		return session != null ? session : createNewSession(kryo);
	}
	
	protected static GameSession createNewSession(Kryo kryo) {
		// we don't have constructor because we should create two of them
		// but i'm too retarded to do this
		final GameSession session = new GameSession();
		session.init();
		
		saveGameSession(kryo, session);
		return session;
	}
	
	protected static void saveGameSession(Kryo kryo, GameSession session) {
		final FileHandle fileHandle = getSaveFileHandle();
		if (!fileHandle.exists()) {
			try {
				fileHandle.file().createNewFile();
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
				return;
			}
		}
		
		final Output output = new Output(fileHandle.write(false));
		kryo.writeObject(output, session);
		output.close();
	}
	
	private static FileHandle getSaveFileHandle() {
		return Gdx.files.local("savefile.crap");
	}
}
