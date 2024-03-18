package by.fxg.clp.render;

import by.fxg.clp.core.GameInputProcessor;
import by.fxg.clp.util.input.InputAdapter;

public abstract class RenderableCanvas {

	public abstract void onUpdate(GameInputProcessor input, RenderManager renderManager, int width, int height);
	public abstract void onRender(RenderManager renderManager, int width, int height, float partialTime);
	
	public void onCanvasClosed(RenderManager renderManager, int width, int height) {
	}
	
	public final boolean requiresInputAdapter() {
		return this.getInputAdapter() != null;
	}
	
	public InputAdapter getInputAdapter() {
		return null;
	}
}
