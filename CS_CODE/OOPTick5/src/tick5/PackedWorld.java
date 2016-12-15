package tick5;

public class PackedWorld extends World implements Cloneable {
	long mWorld;

	public static boolean get(long packed, int position) {
		long check = (packed >> position) % 2;
		return (check == 1 || check == -1);
	}

	public static long set(long packed, int position, boolean value) {
		long i = 0x1;
		if (value) {
			packed = packed | (i << position);
		} else {
			packed = packed & ~(i << position);
		}
		return packed;
	}

	public PackedWorld(String pattern) throws PatternFormatException {
		super(pattern);
		if (getHeight() * getWidth() > 64)
			throw new PatternFormatException("This world is too large for a long!");
		getPattern().initialise(this);
	}

	public PackedWorld(Pattern pattern) throws PatternFormatException {
		super(pattern);
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
