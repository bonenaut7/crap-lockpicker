package by.fxg.clp.session.items;

public class ItemTextured extends Item {
	private final String textureName;
	
	protected ItemTextured(String itemName, String textureName, int maxInstanceSize) {
		super(itemName);
		this.textureName = "items/" + textureName + ".png";
		this.maxInstanceSize = maxInstanceSize;
	}

	@Override
	public String getTexturePath() {
		return this.textureName;
	}
}
