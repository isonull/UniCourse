package S25C;

import S12B.OOPLinkedList;

public class OOPListQueue implements OOPListQueueInterface {
	private OOPLinkedList listA;
	private OOPLinkedList listB;

	public OOPListQueue() {
		// TODO Auto-generated constructor stub
		listA = new OOPLinkedList();
		listB = new OOPLinkedList();
	}

	@Override
	public OOPListQueue enqueue(int x) {
		listB.add(x);
		return this;
	}

	@Override
	public OOPListQueue dequeue() {
		// TODO Auto-generated method stub
		if (this.isEmpty()) {
		} else if (listA.isEmpty()) {
			this.normalise();
			listA.remove();
		} else {
			listA.remove();
		}
		return this;
	}

	@Override
	public OOPListQueue normalise() {
		listA = listA.append(listB.reverse());
		listB.setEmpty();
		return this;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return listA.isEmpty() && listB.isEmpty();
	}

	public static void main(String args[]) {
		OOPListQueue q = new OOPListQueue();
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.dequeue();
		q.dequeue();
		q.dequeue();
		q.dequeue();
	}
}
