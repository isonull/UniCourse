package tick2;

import java.util.Objects;

public class GameOfLife {

	private World mWorld;

	public GameOfLife(World w) {
		mWorld = w;
	}

	public void play() throws Exception {
		// TODO Auto-generated method stub
		int userResponse = 0;
		while (userResponse != 'q') {
			print();
			userResponse = System.in.read();
			mWorld.nextGeneration();
		}
	}

	public void print() {
		System.out.println("- " + mWorld.getGenerationCount());
		for (int i = 0; i != mWorld.getHeight(); ++i) {
			for (int j = 0; j != mWorld.getWidth(); ++j) {
				System.out.print(mWorld.getCell(j, i) ? "#" : "_");
			}
			System.out.println("");
		}
	}

	public static void main(String args[]) throws Exception {

		try {
			World w = null;
			if (args.length == 1) {
				w = new ArrayWorld(args[0]);
			} else if (args.length == 2) {
				if (Objects.equals(args[0], "--array")) {
					w = new ArrayWorld(args[1]);
				} else if (Objects.equals(args[0], "--packed")) {
					w = new PackedWorld(args[1]);
				} else {
					throw new PatternFormatException("Wrong input");
				}
			} else {
				throw new PatternFormatException("Wrong input");
			}
			// TODO: initialise w as an ArrayWorld or a PackedWorld
			// based on the command line input

			GameOfLife gol = new GameOfLife(w);
			gol.play();
		} catch (PatternFormatException e) {
			System.out.println(e.getMessage());
		}
	}

}