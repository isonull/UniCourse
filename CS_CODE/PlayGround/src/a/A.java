package a;

import java.util.List;

public class A {
	public void print() {
		System.out.println("A");
	}

	public void printObject(A a) {
		System.out.println("A");
	}

	public static void printAll(List<B> list) {
		for (A a : list)
			a.print();
	}
}
