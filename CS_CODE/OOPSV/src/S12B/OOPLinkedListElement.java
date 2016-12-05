package S12B;

public class OOPLinkedListElement {
	private int element;
	private OOPLinkedListElement next;

	public OOPLinkedListElement(int x) {
		element = x;
		next = null;
	}

	public OOPLinkedListElement(int x, OOPLinkedListElement xs) {
		element = x;
		next = xs;
	}

	public int getElement() {
		return element;
	}

	public OOPLinkedListElement getNext() {
		return next;
	}

	public void setNext(OOPLinkedListElement newNext) {
		next = newNext;
	}
}
