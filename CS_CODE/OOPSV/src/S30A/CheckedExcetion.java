package S30A;

public class CheckedExcetion {
	public int[] mArray;

	public CheckedExcetion() {
		mArray = new int[5];
	}

	public void setNOnes(int i) throws ArrayIndexOutOfBoundsException {
		for (int j = 0; j < i; j++) {
			mArray[j] = 1;
		}
	}

	public static void main(String args[]) {
		CheckedExcetion a = new CheckedExcetion();
		int i = 6;
		try {
			a.setNOnes(i);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			a.mArray = new int[i];
		}
	}
}
