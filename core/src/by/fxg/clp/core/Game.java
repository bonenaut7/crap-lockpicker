package by.fxg.clp.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.physics.bullet.Bullet;

import by.fxg.clp.render.RenderManager;
import by.fxg.clp.render.canvas.LoadingCanvas;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.util.Timer;

public class Game extends ApplicationAdapter {
	public static boolean DEBUG = true;
	public static Game INSTANCE;
	public static final RandomXS128 rand = new RandomXS128(0xDEADBEEF); // small easter egg :b
	
	private int width, height;
	protected long frameCounter, updateCounter;
	private double lastTickTime;
	
	public GameInputProcessor input;
	public ResourceManager resourceManager;
	public Timer timer;
	public RenderManager renderManager;
	public GameSession gameSession = null;
	
	@Override
	public void create() {
		INSTANCE = this;
		Bullet.init();
		
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		
		this.input = new GameInputProcessor();
		Gdx.input.setInputProcessor(this.input);
		this.resourceManager = new ResourceManager();
		
		this.timer = new Timer(20.0F);
		this.renderManager = new RenderManager(this);
		this.renderManager.setPrimaryCanvas(new LoadingCanvas(this.resourceManager.assetManager));
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	public void onGameLoaded() {
		GameRegistry.init();
		this.gameSession = GameSession.loadGameSession(this.resourceManager.kryo);
		
		// + + + add hangman's audio system 'tuning fork'
		// + + + add logic for merchant offers
		// FIXME complete merchants
		// FIXME complete quests
		// FIXME add serialization for merchant and quests
		// FIXME make models
		// FIXME choose speceditor and add integration
		// FIXME create 3d scene for speceditor levels
		// FIXME add minimal UI to work with
		// FIXME first playtest to check physics
		// FIXME add more FIXME's
	}
	
	public void update() {
		this.updateCounter++;
		this.lastTickTime = this.timer.getLastTimerUpdate();
		
		// Focusing on the game
		if (this.input.isMouseDown(0, false) && !this.input.isCursorCatched() && this.input.getInputAdapter().isCursorCatcheable()) {
			this.input.setCursorCatched(true);
		}
		
		// Unfocusing from the game
		if (this.input.isKeyboardDown(Keys.SYM, false) && this.input.isCursorCatched()) {
			this.input.setCursorCatched(false);
		}
		
		this.renderManager.onUpdate(this.width, this.height);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		this.frameCounter++;
		this.timer.updateTimer();
		final int pendingUpdates = this.timer.getPendingUpdates();
		for (int i = 0; i < pendingUpdates; i++) {
			this.update();
		}
		
		this.renderManager.onRender(this.width, this.height, (float)((this.timer.getLastTimerUpdate() - this.lastTickTime) / this.timer.millisPerUpdate));
	}

	// app window width
	public int getWidth() {
		return this.width;
	}
	
	// app window height
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		this.renderManager.onResize(width, height);
	}
	
	@Override
	public void dispose() {
		this.renderManager.dispose();
		this.resourceManager.dispose();
	}
	
	// OH NOOO
	public static GameSession getSession() {
		return Game.INSTANCE.gameSession;
	}
}
