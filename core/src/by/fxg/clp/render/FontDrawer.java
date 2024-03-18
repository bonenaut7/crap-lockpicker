package by.fxg.clp.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

public class FontDrawer {
	private final Batch batch;
	private BitmapFont font;
	private String string;
	
	private final GlyphLayout layout = new GlyphLayout();
	private boolean isLayoutDirty = true;
	
	public FontDrawer(Batch batch, BitmapFont font) {
		this.batch = batch;
		this.font = font;
	}
	
	public FontDrawer setString(String str) {
		this.string = str;
		this.isLayoutDirty = true;
		return this;
	}

	public FontDrawer draw(float x, float y) {
		return this.draw(this.batch, x, y, Align.center);
	}
	
	public FontDrawer draw(float x, float y, int align) {
		return this.draw(this.batch, x, y, align);
	}
	
	public FontDrawer draw(Batch batch, float x, float y) {
		return this.draw(batch, x, y, Align.center);
	}
	
	public FontDrawer draw(Batch batch, float x, float y, int align) {
		this.font.draw(batch, this.string, x, y, 0, align, false);
		return this;
	}
	
	public void draw(String text, float x, float y) {
		this.font.draw(this.batch, text, x, y, 0, Align.center, false);
	}
	
	public void draw(String text, float x, float y, int align) {
		this.font.draw(this.batch, text, x, y, 0, align, false);
	}
	
	public void draw(Batch batch, String text, float x, float y, int align) {
		this.font.draw(batch, text, x, y, 0, align, false);
	}
	
	public void setFont(BitmapFont font) {
		this.font = font;
		this.isLayoutDirty = true;
	}

	public String getString() {
		return this.string;
	}
	
	public float getHalfHeight() {
		return this.getHeight() / 2;
	}
	
	public float getHalfWidth() {
		return this.getWidth() / 2;
	}
	
	public float getHeight() {
		this.updateLayout();
		return layout.height;
	}
	
	public float getWidth() {
		this.updateLayout();
		return layout.width;
	}
	
	private void updateLayout() {
		if (this.isLayoutDirty) {
			this.isLayoutDirty = false;
			this.layout.setText(this.font, this.string);
		}
	}
}
