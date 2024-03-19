package by.fxg.clp.util;

import com.badlogic.gdx.math.MathUtils;

public final class IntRange {
	public static final IntRange ZERO = new IntRange(0, 0);
	
	private final int min;
	private final int max;
	
	public IntRange(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public int getMin() {
		return this.min;
	}
	
	public int getMax() {
		return this.max;
	}
	
	public int random() {
		return MathUtils.random(this.min, this.max);
	}
	
	public boolean isInRange(int value) {
		return this.min <= value && value <= this.max;
	}
	
	public static IntRange of(int min, int max) {
		return new IntRange(min, max);
	}
}
