package by.fxg.clp.registry.merchant;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import by.fxg.clp.core.Game;
import by.fxg.clp.core.ResourceManager;

public abstract class Merchant implements Disposable {
	public static final Array<Merchant> randomlyAccessibleMerchants = new Array<>();
	
	// Internals
	protected final String name;
	protected final Texture texture;
	protected final Decal decal;
	
	// Generation
	private float generationChance = 0;
	
	// Offers generation
	protected long offersSeed0, offersSeed1;
	
	public Merchant(String name, String textureName, float sizeX, float sizeY) {
		this.name = name;
		
		final ResourceManager resourceManager = Game.INSTANCE.resourceManager;
		this.texture = resourceManager.getAssetWithFallback(Texture.class, "merchants/" + textureName + ".png", resourceManager.getPlaceholderTexture());
		this.decal = Decal.newDecal(new TextureRegion(this.texture), true);
		this.decal.setWidth(sizeX);
		this.decal.setHeight(sizeY);
	}
	
	// chance is 0.0-1.0
	protected final void setRandomlyAccessible(boolean isRandomlyGenerated, float chance) {
		if (isRandomlyGenerated) {
			randomlyAccessibleMerchants.add(this);
		} else {
			randomlyAccessibleMerchants.removeValue(this, true);
		}
		
		this.generationChance = chance;
	}
	
	public float getGenerationChance() {
		return this.generationChance;
	}
	
	/** Updates seed to randomly generated one **/
	public void updateSeed() {
		this.setSeed(MathUtils.random.nextLong(), MathUtils.random.nextLong());
	}
	
	/** Sets specified seed **/
	public void setSeed(long seed0, long seed1) {
		this.offersSeed0 = seed0;
		this.offersSeed1 = seed1;
	}

	/** Refreshes offers **/
	public abstract void refreshOffers();
	
	/** Returns random merchant
	 *  @param noMerchantChance - chance to get null(no merchant), 0.0-1.0 **/
	public static Merchant getRandomMerchant(float noMerchantChance) {
		if (randomlyAccessibleMerchants.size < 1) throw new IllegalStateException("There's no generatable merchants!");
		
		if (MathUtils.randomBoolean(noMerchantChance)) {
			return null;
		}
		
		while (true) {
			final Merchant selectedMerchant = randomlyAccessibleMerchants.random();
			if (MathUtils.randomBoolean(selectedMerchant.getGenerationChance())) {
				return selectedMerchant;
			}
		}
	}
}
