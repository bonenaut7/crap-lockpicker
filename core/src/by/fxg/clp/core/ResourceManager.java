package by.fxg.clp.core;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryo.Kryo;

import by.fxg.clp.session.GameInventory;
import by.fxg.clp.session.GameSession;
import by.fxg.clp.session.GameSettings;
import by.fxg.clp.session.items.ItemInstance;
import by.fxg.clp.util.JarUtils;
import by.fxg.clp.util.serialization.GameInventorySerializer;
import by.fxg.clp.util.serialization.GameSessionSerializer;
import by.fxg.clp.util.serialization.GameSettingsSerializer;
import by.fxg.clp.util.serialization.ItemInstanceSerializer;
import de.pottgames.tuningfork.SoundBuffer;
import de.pottgames.tuningfork.SoundBufferLoader;

public class ResourceManager implements Disposable {
	private final Map<String, Class<?>> assetMarkers = new HashMap<>();
	protected final AssetManager assetManager;
	public final Kryo kryo;
	
	private final Texture placeholderTexture;
	public final TextureRegion shapeDrawerTexture;
	public final BitmapFont fontSmall, fontMedium, fontLarge;
	
	public ResourceManager() {
		this.assetMarkers.put("png", Texture.class);
		this.assetMarkers.put("obj", Model.class);
		this.assetMarkers.put("ogg", SoundBuffer.class);
		this.assetMarkers.put("mp3", SoundBuffer.class);
		
		this.assetManager = new AssetManager();
		this.assetManager.setLoader(SoundBuffer.class, new SoundBufferLoader(new InternalFileHandleResolver()));
		
		this.kryo = new Kryo();
		this.kryo.register(GameSession.class, new GameSessionSerializer());
		this.kryo.register(GameSettings.class, new GameSettingsSerializer());
		this.kryo.register(GameInventory.class, new GameInventorySerializer());
		this.kryo.register(ItemInstance.class, new ItemInstanceSerializer());
		
		// Create placeholders
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		this.shapeDrawerTexture = new TextureRegion(new Texture(pixmap), 0, 0, 1, 1);
		pixmap.dispose();
		
		pixmap = new Pixmap(2, 2, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.drawRectangle(0, 0, 2, 2);
		pixmap.drawPixel(0, 0, 0xFB3EF9FF);
		pixmap.drawPixel(0, 0, 0xFB3EF9FF);
		this.placeholderTexture = new Texture(pixmap);
		pixmap.dispose();
		
		// Load fonts
		this.fontSmall = this.loadFont(Gdx.files.internal("assets/font/monogram.ttf"), 16, false);
		this.fontMedium = this.loadFont(Gdx.files.internal("assets/font/monogram.ttf"), 32, false);
		this.fontLarge = this.loadFont(Gdx.files.internal("assets/font/monogram.ttf"), 64, false);
	
		// Queue resources
		this.batchResources("assets/", -1);
	}
	
	public void batchResources(String path, int recursivityLevel) {
		for (FileHandle fh : JarUtils.listFromJarIfNecessary(path, true)) {
			if (!fh.isDirectory()) {
				for (String str : this.assetMarkers.keySet()) {
					if (fh.extension().equalsIgnoreCase(str)) {
						this.assetManager.load(fh.path(), this.assetMarkers.get(str));
					}
				}
			} else if (recursivityLevel > 0 || recursivityLevel == -1) {
				this.batchResources(path + fh.name() + "/", recursivityLevel == -1 ? recursivityLevel : recursivityLevel - 1);
			}
		}
	}
	
	public Texture getPlaceholderTexture() {
		return this.placeholderTexture;
	}
	
	public <T> T getAsset(Class<T> type, String path) {
		return this.assetManager.get("assets/" + path, type);
	}
	
	public <T> T getAssetWithFallback(Class<T> type, String path, T fallbackAsset) {
		return this.isAssetLoaded(path, type) ? this.getAsset(type, path) : fallbackAsset;
	}
	
	public boolean isAssetLoaded(String path, Class<?> type) {
		return this.assetManager.isLoaded("assets/" + path, type);
	}
	
	private BitmapFont loadFont(FileHandle fontFile, int size, boolean flip) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.packer = new PixmapPacker(4096, 4096, Format.RGBA8888, 2, false);
		parameter.hinting = Hinting.AutoSlight;
		parameter.flip = flip;
		parameter.size = size;
		parameter.incremental = true;
		return generator.generateFont(parameter);
	}

	@Override
	public void dispose() {
		this.assetManager.dispose();
		this.placeholderTexture.dispose();
		this.shapeDrawerTexture.getTexture().dispose();
		this.fontSmall.dispose();
	}
}
