package S35C;

public class MyClass implements Cloneable {
	private String mName;
	private int[] mData;

	public MyClass(MyClass toCopy) {
		this.mName = toCopy.mName;
		this.mData = new int[toCopy.mData.length];
		for (int i : mData) {
			this.mData[i] = toCopy.mData[i];
		}
	}

	public MyClass clone() throws CloneNotSupportedException {
		MyClass clone = (MyClass) super.clone();
		mData = mData.clone(); // However, if the mData is final, a copy
								// constructor must be used.
		return clone;
	}
}
