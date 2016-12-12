package test;

public class Int {
	private int i;

	public Int(int ii) {
		i = ii;
	}

	public void increment() {
		i++;
	}

	public Object clone() throws CloneNotSupportedException {
		Object i = null;
		i = super.clone();
		return i;
	}

	public String toString() {
		return Integer.toString(i);
	}

	public static void main(String args[]) {
		int i = 0;
		System.out.println(i++);
	}
}
