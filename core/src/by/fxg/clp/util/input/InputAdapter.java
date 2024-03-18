package by.fxg.clp.util.input;

public interface InputAdapter {
	void inputUpdate();
	
	void onCameraMoved(float amountX, float amountY);
	
	// defines whenever cursor is able to be 'catched'
	boolean isCursorCatcheable();
}
