package a;

import java.util.LinkedList;
import java.util.List;

public class B extends A {
	public void print() {
		System.out.println("B");
	}

	public void printObject(B b) {
		System.out.println("B");
	}

	public static void main(String[] args) {
		A a = new A();

		B b = new B();
		List<A> alist = new LinkedList<A>();
		alist.add(a);
		alist.add(b);
		LinkedList<B> blist = new LinkedList<B>();
		blist.add(b);
		// ((B) a).print();
		((A) b).print();
		A.printAll(alist);
		b.printAll(blist);
		a.printObject(b);
		// b.printObject((B) a);
		b.printObject((A) b);
	}
}