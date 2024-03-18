package by.fxg.clp.render.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Align;

import by.fxg.clp.core.Game;
import by.fxg.clp.core.GameInputProcessor;
import by.fxg.clp.render.RenderManager;
import by.fxg.clp.render.RenderableCanvas;

public class LoadingCanvas extends RenderableCanvas {
	private AssetManager assetManager;
	
	public LoadingCanvas(AssetManager assetManager) {
		this.assetManager = assetManager;
	}
	
	@Override
	public void onUpdate(GameInputProcessor input, RenderManager renderManager, int width, int height) {
		if (!this.assetManager.isFinished()) { // Load assets
			if (this.assetManager.update((int)(Gdx.graphics.getDeltaTime() * 1000))) {
				this.assetManager.finishLoading();
			}
		} else {
			renderManager.setPrimaryCanvas(null); // Remove LoadingCanvas
			Game.INSTANCE.onGameLoaded();
		}
	}

	@Override
	public void onRender(RenderManager renderManager, int width, int height, float partialTime) {
		renderManager.spriteBatch.begin();
		renderManager.fontDrawer.setString("Loading " + (int)(this.assetManager.getProgress() * 100f) + "%").draw(10, 10 + renderManager.fontDrawer.getHeight(), Align.left);
		renderManager.spriteBatch.end();
	}
}
