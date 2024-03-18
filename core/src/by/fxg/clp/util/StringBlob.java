package by.fxg.clp.util;

import com.badlogic.gdx.math.MathUtils;

// it's just object that contains some string data in arrays
// StringBlob is being read from files, each new line is each element in the array
public class StringBlob {
	private final boolean isEmpty;
	private final String[] array;
	
	public StringBlob(String[] array) {
		this.isEmpty = array == null || array.length == 0;
		this.array = array;
	}
	
	public String random() {
		return this.isEmpty ? null :
			this.array[MathUtils.random.nextInt(this.array.length)];
	}
	
	public String get(int index) {
		return this.isEmpty ? null :
			index > -1 && index < this.array.length ? this.array[index] : null;
	}
	
	public int getSize() {
		return this.isEmpty ? 0 : this.array.length;
	}
}
