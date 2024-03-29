package tick2Star;

public class PackedWorld extends World {
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
		if (getHeight() > 8 || getWidth() > 8)
			throw new PatternFormatException("This should be an 8X8 world!");

		getPattern().initialise(this);

	}

	@Override
	protected void nextGenerationimpl() {
		// TODO Auto-generated method stub
		long nextGeneration = 0x0;
		for (int y = 0; y < 8; ++y) {
			for (int x = 0; x < 8; ++x) {
				nextGeneration = set(nextGeneration, (8 * (8 - y) - x), computeCell(x, y));
			}
		}
		mWorld = nextGeneration;
	}

	@Override
	public boolean getCell(int x, int y) {
		return get(mWorld, (8 * (8 - y) - x));
	}

	@Override
	public void setCell(int x, int y, boolean val) {
		mWorld = set(mWorld, (8 * (8 - y) - x), val);
	}

}
