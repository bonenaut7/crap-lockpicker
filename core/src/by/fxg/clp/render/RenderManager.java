package by.fxg.clp.render;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.utils.Disposable;

import by.fxg.clp.core.Game;
import by.fxg.gdxpsx.g3d.PSXShaderProvider;
import by.fxg.gdxpsx.postprocessing.PSXPostProcessing;
import by.fxg.gdxpsx.postprocessing.PSXPostProcessingWrapper;
import space.earlygrey.shapedrawer.ShapeDrawer;

public final class RenderManager implements Disposable {
	private final Game game;
	public final BitmapFont fontSmall, fontMedium, fontLarge;
	
	public PolygonSpriteBatch spriteBatch;
	public ModelBatch modelBatch;
	public ShapeDrawer shapeDrawer;
	public FontDrawer fontDrawer;
	public PSXPostProcessing psxProperties;
	public PSXPostProcessingWrapper postProcessing;

	private RenderableCanvas primaryCanvas = null;
	private RenderableCanvas secondaryCanvas = null;
	
	public RenderManager(Game game) {
		this.game = game;
		this.fontSmall = this.game.resourceManager.fontSmall;
		this.fontMedium = this.game.resourceManager.fontMedium;
		this.fontLarge = this.game.resourceManager.fontLarge;
		
		this.spriteBatch = new PolygonSpriteBatch();
		this.modelBatch = new ModelBatch(new PSXShaderProvider(new DefaultShader.Config() {{
			this.numPointLights = 10;
		}}));
		this.shapeDrawer = new ShapeDrawer(this.spriteBatch, game.resourceManager.shapeDrawerTexture);
		this.fontDrawer = new FontDrawer(this.spriteBatch, game.resourceManager.fontLarge);
		this.psxProperties = new PSXPostProcessing().setDefaultParametersWithResolution();
		this.postProcessing = new PSXPostProcessingWrapper(this.psxProperties);
	}
	
	public void onUpdate(int width, int height) {
		if (this.secondaryCanvas != null) {
			this.secondaryCanvas.onUpdate(this.game.input, this, width, height);
			return;
		}
		
		if (this.primaryCanvas != null) {
			this.primaryCanvas.onUpdate(this.game.input, this, width, height);
		}
	}
	
	public void onRender(int width, int height, float partialTime) {
		if (this.secondaryCanvas != null) {
			this.secondaryCanvas.onRender(this, width, height, partialTime);
			return;
		}
		
		if (this.primaryCanvas != null) {
			this.primaryCanvas.onRender(this, width, height, partialTime);
		}
	}
	
	public void setPrimaryCanvas(RenderableCanvas canvas) {
		if (this.primaryCanvas != null) {
			this.primaryCanvas.onCanvasClosed(this, this.game.getWidth(), this.game.getHeight());
		}
		
		this.primaryCanvas = canvas;
		this.updateInputAdapter();
	}
	
	public void setSecondaryCanvas(RenderableCanvas canvas) {
		if (this.secondaryCanvas != null) {
			this.secondaryCanvas.onCanvasClosed(this, this.game.getWidth(), this.game.getHeight());
		}
		
		this.secondaryCanvas = canvas;
		this.updateInputAdapter();
	}
	
	public void updateInputAdapter() {
		if (this.secondaryCanvas != null) {
			this.game.input.setInputAdapter(this.secondaryCanvas.getInputAdapter());
			return;
		}
		
		if (this.primaryCanvas != null) {
			this.game.input.setInputAdapter(this.primaryCanvas.getInputAdapter());
			return;
		}
		
		this.game.input.setInputAdapter(null);
	}
	
	public void onResize(int width, int height) {
		this.psxProperties.setInputResolution(width, height);
	}
	
	@Override
	public void dispose() {
		this.spriteBatch.dispose();
		this.modelBatch.dispose();
		this.psxProperties.dispose();
		this.postProcessing.dispose();
	}
}
