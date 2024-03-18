package by.fxg.clp.session;

public class GameSettings {
	// settings format
	public static final short VERSION = 01;
	
	// Sound levels, 0.0 - 1.0 floats. @since V01
	public float masterSoundLevel = 1.0f;
	public float musicSoundLevel = 1.0f;
	public float sfxSoundLevel = 1.0f;
	
	// Controls settings. @since V01
	public float mouseSensetivityX = 0.2f;
	public float mouseSensetivityY = 0.2f;
	public boolean mouseInvertAxisY = false;
}
