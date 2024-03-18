package by.fxg.clp.util;

public final class IntRange {
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
	
	public static IntRange of(int min, int max) {
		return new IntRange(min, max);
	}
}
