package tick2;

public class Pattern {

	PatternFormatException e = new PatternFormatException("A message");

	private String mName;
	private String mAuthor;
	private int mWidth;
	private int mHeight;
	private int mStartCol;
	private int mStartRow;
	private String mCells;

	// TODO: write public 'get' methods for ALL of the fields above;
	// for instance 'getName' should be written as:
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
		return mWidth;
	}

	public int getStartRow() {
		return mWidth;
	}

	public String getCells() {
		return mCells;
	}

	public Pattern(String format) throws PatternFormatException {
		// TODO: initialise all fields of this class using contents of
		// 'format' to determine the correct values (this code
		// is similar to that you used in the new ArrayLife constructor

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
		// TODO: update the values in the 2D array representing the state of
		// 'world' as expressed by the contents of the field 'mCells'.

		String[] pattern = mCells.split(" ");

		int patternWidth = pattern[0].length();
		int patternHeight = pattern.length;

		try {
			for (int row = 0; row < mHeight; ++row) {
				for (int col = 0; col < mWidth; ++col) {
					// world[row][col] = false;
					world.setCell(col, row, false);
				}
			}

			for (int row = 0; row < mStartRow + patternHeight; ++row) {
				if (row < mStartRow)
					continue;
				for (int col = 0; col < mStartCol + patternWidth; ++col) {
					if (col < mStartCol)
						continue;
					char zeroOne = pattern[row - mStartRow].charAt(col - mStartCol);
					if (zeroOne == '1')
						world.setCell(col, row, true);// world[row][col] = true;
					else if (zeroOne == '0')
						world.setCell(col, row, false); // world[row][col] =
														// false;
					else
						throw new PatternFormatException("Malformed pattern " + mCells);
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			throw new PatternFormatException("Invalid Length of Pattern.");
		}
	}
}
