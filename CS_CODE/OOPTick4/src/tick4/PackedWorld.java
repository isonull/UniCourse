package tick4;

public class PackedWorld extends World implements Cloneable {
	long mWorld;

	public static boolean get(long packed, int position) {
		// set "check" to equal 1 if the "position" bit in "packed" is set to 1
		long check = (packed >> position) % 2; // TODO: complete this statement
		return (check == 1 || check == -1);
	}

	/*
	 * Set the nth bit in the packed number to the value given and return the
	 * new packed number
	 */
	public static long set(long packed, int position, boolean value) {
		long i = 0x1;
		if (value) {
			packed = packed | (i << position);
			// TODO: complete this
			// update the value "packed" with the bit at "position" set to 1
		} else {
			packed = packed & ~(i << position);
			// TODO: complete this
			// update the value "packed" with the bit a "position" set to 0
		}
		return packed;
	}

	public PackedWorld(String pattern) throws PatternFormatException {
		super(pattern);
		// TODO Auto-generated constructor stub
		if (getHeight() * getWidth() > 64)
			throw new PatternFormatException("This world is too large for a long!");
		getPattern().initialise(this);
	}

	public PackedWorld(Pattern pattern) throws PatternFormatException {
		super(pattern);
		// TODO Auto-generated constructor stub
		if (getHeight() * getWidth() > 64)
			throw new PatternFormatException("This world is too large for a long!");
		getPattern().initialise(this);
	}

	public PackedWorld(PackedWorld w) {
		super(w);
		mWorld = w.mWorld;
	}

	@Override
	public PackedWorld clone() throws CloneNotSupportedException {
		PackedWorld n = (PackedWorld) super.clone();
		n.mWorld = this.mWorld;
		return n;
	}

	@Override
	protected void nextGenerationimpl() {
		// TODO Auto-generated method stub
		long nextGeneration = 0x0;
		for (int y = 0; y < 8; ++y) {
			for (int x = 0; x < 8; ++x) {
				nextGeneration = set(nextGeneration, (getWidth() * (getHeight() - y) - x), computeCell(x, y));
			}
		}
		mWorld = nextGeneration;
	}

	@Override
	public boolean getCell(int x, int y) {
		return get(mWorld, (getWidth() * (getHeight() - y) - x));
	}

	@Override
	public void setCell(int x, int y, boolean val) {
		mWorld = set(mWorld, (getWidth() * (getHeight() - y) - x), val);
	}

}
