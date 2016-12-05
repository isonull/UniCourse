package S12B;

public class OOPLinkedList {
	private OOPLinkedListElement head;

	public OOPLinkedList() {
		head = null;
	}

	public OOPLinkedList(OOPLinkedListElement h) {
		head = h;
	}

	protected OOPLinkedList setHead(OOPLinkedListElement e) {
		head = e;
		return this;
	}

	protected OOPLinkedListElement getHead() {
		return head;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void setEmpty() {
		head = null;
	}

	public OOPLinkedList add(int x) {
		head = new OOPLinkedListElement(x, head);
		return this;
	}

	public OOPLinkedList remove() {
		if (head != null) {
			// int i = head.getElement();
			head = head.getNext();
			return this;
		} else {
			return this;
		}
	}

	public int get() throws Exception {
		if (head == null)
			throw new Exception("Empty OOPLinkedList.");
		else
			return head.getElement();
	}

	public int length() {
		if (head == null)
			return 0;
		int len = 1;
		OOPLinkedListElement p = head;
		while (p.getNext() != null) {
			++len;
			p = p.getNext();
		}
		return len;
	}

	public OOPLinkedList reverse() {

		int len = this.length();
		OOPLinkedList reversedList = new OOPLinkedList();
		int hh;
		try {
			for (int i = 0; i < len; ++i) {

				hh = this.get();

				reversedList.add(hh);
				this.remove();
			}
		} catch (Exception e) {
			System.out.println("This should never happen.");
			e.printStackTrace();
		}

		head = reversedList.head;
		return reversedList;

	}

	public OOPLinkedList append(OOPLinkedList tail) {

		if (this.isEmpty())
			return tail;
		int originHeadElement;
		try {
			originHeadElement = this.get();
			return (this.remove().append(tail)).add(originHeadElement);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("This should never happen.");
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String args[]) throws Exception {
		OOPLinkedList ls = new OOPLinkedList();
		OOPLinkedList ps = new OOPLinkedList();
		ls.add(1);
		ls.add(2);
		ls.add(3);
		ps.add(4);
		ps.add(5);
		ps.add(6);

		ls = ls.append(ps);
		ls.reverse();
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
