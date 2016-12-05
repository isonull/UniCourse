package S47B;

public class IntPair implements Comparable<IntPair> {
	private int first;
	private int second;

	public IntPair(int a, int b) {
		first = a;
		second = b;
	}

	@Override
	public int compareTo(IntPair o) {
		if (this.first > o.first) {
			return 1;
		} else if (this.first < o.first) {
			return -1;
		} else {
			if (this.second > o.second) {
				return 1;
			} else if (this.second < o.second) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public int first() {
		return first;
	}

	public int second() {
		return second;
	}
}
