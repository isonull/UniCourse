package tick5;

public class Pattern implements Comparable<Pattern> {

	PatternFormatException e = new PatternFormatException("A message");

	private String mName;
	private String mAuthor;
	private int mWidth;
	private int mHeight;
	private int mStartCol;
	private int mStartRow;
	private String mCells;

	public String getName() {
		return mName;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	public int getStartCol() {
		return mStartCol;
	}

	public int getStartRow() {
		return mStartRow;
	}

	public String getCells() {
		return mCells;
	}

	@Override
	public String toString() {
		return mName + "(" + mAuthor + ")";

	}

	public Pattern(String format) throws PatternFormatException {

		if (format.isEmpty())
			throw new PatternFormatException("Please specify a pattern.");
		String[] input = format.split(":");
		int inputLength = input.length;
		if (inputLength != 7)
			throw new PatternFormatException(
					"Invalid pattern format: Incorrect number of fields in pattern (found " + inputLength + ").");
		mName = input[0];
		mAuthor = input[1];
		try {
			mWidth = Integer.parseInt(input[2]);
			mHeight = Integer.parseInt(input[3]);
			mStartCol = Integer.parseInt(input[4]);
			mStartRow = Integer.parseInt(input[5]);
		} catch (java.lang.NumberFormatException numberFormatException) {
			throw new PatternFormatException(
					"Could not interpret the width field as a number: " + numberFormatException.getMessage());
		}
		mCells = input[6];

	}

	public void initialise(World world) throws PatternFormatException {

		String[] pattern = mCells.split(" ");

		int patternWidth = pattern[0].length();
		int patternHeight = pattern.length;

		try {
			for (int row = 0; row < mStartRow + patternHeight; ++row) {
				if (row < mStartRow)
					continue;
				for (int col = 0; col < mStartCol + patternWidth; ++col) {
					if (col < mStartCol)
						continue;
					char zeroOne = pattern[row - mStartRow].charAt(col - mStartCol);
					if (zeroOne == '1')
						world.setCell(col, row, true);
					else if (zeroOne == '0')
						world.setCell(col, row, false);
					else
						throw new PatternFormatException("Malformed pattern " + mCells);
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			throw new PatternFormatException("Invalid Length of Pattern.");
		}
	}

	@Override
	public int compareTo(Pattern p) {
		return mName.compareTo(p.mName);
	}
}