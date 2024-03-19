package by.fxg.clp.registry.merchant;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import by.fxg.clp.core.Game;
import by.fxg.clp.core.ResourceManager;

public abstract class Merchant {
	public static final Array<Merchant> randomlyAccessibleMerchants = new Array<>();
	
	// Internals
	protected final String name;
	protected final Texture texture;
	protected final Decal decal;
	
	// Generation
	private float generationChance = 0;
	
	// Offers
	protected Array<MerchantOffer> offers = new Array<>();
	
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
	
	/** Refreshes offers **/
	public abstract void refreshOffers();
	
	/** Returns offers array **/
	public Array<MerchantOffer> getOffers() {
		return this.offers;
	}
	
	/** Returns balanced random chance for sale offer based
	 *    on total amount of sale and purchase offers **/
	protected float getBalancedRandom(int saleOffers, int purchaseOffers) {
		if (saleOffers < 1) return 1;
		if (purchaseOffers < 1) return 0;
		
		final float saleFactor = 1.0f / saleOffers;
		final float purchaseFactor = 1.0f / purchaseOffers;
		return saleFactor < purchaseFactor ? (purchaseOffers * saleFactor) : (saleOffers * purchaseFactor);
	}
	
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
