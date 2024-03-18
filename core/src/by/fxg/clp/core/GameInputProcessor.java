package by.fxg.clp.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import by.fxg.clp.session.GameSettings;
import by.fxg.clp.util.input.InputAdapter;
import by.fxg.clp.util.input.ScreenInputAdapter;

public class GameInputProcessor implements InputProcessor {
	private static final InputAdapter STANDARD_INPUT_ADAPTER = new ScreenInputAdapter();
	
	private long[] mouseScrollTime = new long[3];
	private long[] mouseClickTime = new long[5];
	private long[] keyboardClickTime = new long[255/*180*/];
	private float mouseTempX, mouseTempY;
	private InputAdapter inputAdapter = STANDARD_INPUT_ADAPTER;
	
	public void setInputAdapter(InputAdapter inputAdapter) {
		this.inputAdapter = inputAdapter != null ? inputAdapter : STANDARD_INPUT_ADAPTER;
		
		final boolean targetCursorState = this.inputAdapter.isCursorCatcheable();
		if (Gdx.input.isCursorCatched() != targetCursorState) {
			this.setCursorCatched(targetCursorState);
		}
	}
	
	public InputAdapter getInputAdapter() {
		return this.inputAdapter;
	}
	
	public void setCursorCatched(boolean isCatched) {
		Gdx.input.setCursorCatched(isCatched);
		if (isCatched) {
			Gdx.input.setCursorPosition((int)this.mouseTempX, (int)this.mouseTempY);
		}
	}
	
	public boolean isCursorCatched() {
		return Gdx.input.isCursorCatched();
	}
	
	public long getClickedMouseTime(int key) {
		return Game.INSTANCE.updateCounter - this.mouseClickTime[key];
	}
	
	public long getClickedKeyTime(int key) {
		return Game.INSTANCE.updateCounter - this.keyboardClickTime[key];
	}
	
	public boolean isMouseScrolled(boolean up) {
		return this.mouseScrollTime[up ? 2 : 0] == Game.INSTANCE.updateCounter - 1;
	}
	
	public boolean isMouseDown(int key, boolean isHold) {
		return isHold ? this.mouseClickTime[key] > 0L : this.mouseClickTime[key] == Game.INSTANCE.updateCounter - 1;
	}
	
	public boolean isKeyboardDown(int key, boolean isHold) {
		return isHold ? this.keyboardClickTime[key] > 0L : this.keyboardClickTime[key] == Game.INSTANCE.updateCounter - 1;
	}
	
	public int getMouseX() {
		return Gdx.input.getX();
	}
	
	public int getMouseY() {
		return Gdx.graphics.getHeight() + -Gdx.input.getY();
	}
	
	public boolean isMouseIn(float x, float y, float x1, float y1) {
		return x <= Gdx.input.getX() && Gdx.input.getX() <= x1 && y <= (Gdx.graphics.getHeight() + -Gdx.input.getY()) && (Gdx.graphics.getHeight() + -Gdx.input.getY()) <= y1;
	}
	
	public boolean isMouseInArea(float x, float y, float w, float h) {
		return this.isMouseIn(x, y, x + w, y + h);
	}
	
	// Implementation
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode >= 0) {
			this.keyboardClickTime[keycode] = Game.INSTANCE.updateCounter;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode >= 0) {
			this.keyboardClickTime[keycode] = 0;
			return true;
		}
		return false;
	}

	@Override
	@Deprecated
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button >= 0) {
			this.mouseClickTime[button] = Game.INSTANCE.updateCounter;
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button >= 0) {
			this.mouseClickTime[button] = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return this.touchUp(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (this.isCursorCatched()) {
			float diffX = this.mouseTempX - screenX;
			float diffY = this.mouseTempY - screenY;
			
			if (Game.INSTANCE.gameSession != null) {
				final GameSettings gameSettings = Game.INSTANCE.gameSession.gameSettings;
				
				diffX *= gameSettings.mouseSensetivityX;
				diffY *= gameSettings.mouseSensetivityY;
				
				if (gameSettings.mouseInvertAxisY) {
					diffY = -diffY;
				}
			}
			
			this.inputAdapter.onCameraMoved(diffX, diffY);
			
			this.mouseTempX = diffX;
			this.mouseTempY = diffY;
		}
		// else set mx,my to 0
		return false; // FIXME change this later
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return this.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		this.mouseScrollTime[amountY > 0.0 ? 2 : 0] = Game.INSTANCE.updateCounter;
		return true;
	}

}
