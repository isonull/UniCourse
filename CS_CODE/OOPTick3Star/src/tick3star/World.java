package tick3star;

public abstract class World {

	private int mGeneration;
	private Pattern mPattern;

	public World(String pattern) throws PatternFormatException {
		mGeneration = 0;
		mPattern = new Pattern(pattern);
	}

	public World(Pattern pattern) {
		mGeneration = 0;
		mPattern = pattern;
	}

	public World(World o) {
		mGeneration = o.mGeneration;
		mPattern = o.mPattern;
	}

	public int getWidth() {
		return mPattern.getWidth();
	}

	public int getHeight() {
		return mPattern.getHeight();
	}

	public int getGenerationCount() {

		return mGeneration;
	}

	// protected void incrementGenerationCount() {}

	protected Pattern getPattern() {
		return mPattern;
	}

	public void nextGeneration() {
		++mGeneration;
		nextGenerationimpl();
	}

	protected abstract void nextGenerationimpl();

	public abstract boolean getCell(int x, int y);

	public abstract void setCell(int x, int y, boolean val);

	protected int countNeighbours(int x, int y) {
		int n = 0;
		if (x >= 0 && x < mPattern.getWidth() && y >= 0 && y < mPattern.getHeight()) {
			n = (x >= 1 && y >= 1 && getCell(x - 1, y - 1)) ? ++n : n;
			n = (x >= 1 && y < mPattern.getHeight() - 1 && getCell(x - 1, y + 1)) ? ++n : n;
			n = (x < mPattern.getWidth() - 1 && y >= 1 && getCell(x + 1, y - 1)) ? ++n : n;
			n = (x < mPattern.getWidth() - 1 && y < mPattern.getHeight() - 1 && getCell(x + 1, y + 1)) ? ++n : n;
			n = (x >= 1 && getCell(x - 1, y)) ? ++n : n;
			n = (x < mPattern.getWidth() - 1 && getCell(x + 1, y)) ? ++n : n;
			n = (y >= 1 && getCell(x, y - 1)) ? ++n : n;
			n = (y < mPattern.getHeight() - 1 && getCell(x, y + 1)) ? ++n : n;
		} else {
			return 0;
		}
		return n;
	}

	protected boolean computeCell(int x, int y) {
		int i = countNeighbours(x, y);
		if (i < 2 || i > 3)
			return false;
		if (i == 3)
			return true;
		if (i == 2 && getCell(x, y))
			return true;
		else
			return false;
	}

	public boolean worldEqual(World o) {
		int mWidth = this.getWidth();
		int mHeight = this.getHeight();
		int oWidth = o.getWidth();
		int oHeight = o.getHeight();
		if (mWidth != oWidth || mHeight != oHeight)
			return false;
		for (int y = 0; y < mHeight; ++y) {
			for (int x = 0; x < mWidth; ++x) {
				if (getCell(x, y) != o.getCell(x, y))
					return false;
			}
		}
		return true;
	}

	public int numberOfLives() {
		int num = 0;
		for (int y = 0; y < this.getHeight(); ++y) {
			for (int x = 0; x < this.getWidth(); ++x) {
				if (getCell(x, y) == true) {
					++num;
				}
			}
		}
		return num;
	}
}
