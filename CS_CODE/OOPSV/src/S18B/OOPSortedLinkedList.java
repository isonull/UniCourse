package S18B;

import S12B.OOPLinkedList;
import S12B.OOPLinkedListElement;

public class OOPSortedLinkedList extends OOPLinkedList {

	public OOPSortedLinkedList() {
		super();
	}

	public OOPSortedLinkedList add(int x) {
		if (this.isEmpty()) {
			this.setHead(new OOPLinkedListElement(x, null));
			return this;
		}
		try {
			if (x < this.get()) {
				this.setHead(new OOPLinkedListElement(x, this.getHead()));
				return this;
			}
		} catch (Exception e) {
			System.out.println("This should never happen.");
			e.printStackTrace();
		}
		OOPLinkedListElement before = this.getHead();
		while (before.getNext() != null && x > before.getNext().getElement()) {
			before = before.getNext();
		}
		before.setNext(new OOPLinkedListElement(x, before.getNext()));
		return this;
	}

	public static void main(String args[]) throws Exception {
		OOPSortedLinkedList ls = new OOPSortedLinkedList();
		ls.add(1);
		ls.add(5);
		ls.add(3);
		ls.add(0);
		System.out.println("len:" + ls.length());
		System.out.println(ls.get());
		ls.remove();
		System.out.println("len:" + ls.length());
		System.out.println(ls.get());
		ls.remove();
		System.out.println("len:" + ls.length());
		System.out.println(ls.get());
		ls.remove();
		System.out.println("len:" + ls.length());
		System.out.println(ls.get());
		System.out.println(ls.get());
	}

}
