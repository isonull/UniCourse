package test;

import java.util.Vector;

public class cloning {
	public static void main(String args[]) {
		Int i = new Int(0);
		Vector<Int> v = new Vector<>(1);
		v.addElement(i);
		Vector<Integer> v2 = new Vector<>();
		v2 = (Vector<Integer>) v.clone();

		i.increment();

	}
}
