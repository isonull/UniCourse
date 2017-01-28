package tick1;

public interface MaxCharHeapInterface {
	// Get and remove the maximum value (or exception if empty)
	public char getMax() throws EmptyHeapException;

	// Insert a new value into the heap
	public void insert(char i);

	// Get the number of items in the heap
	public int getLength();
}
