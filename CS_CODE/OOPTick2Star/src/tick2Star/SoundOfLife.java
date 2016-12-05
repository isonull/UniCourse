package tick2Star;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import uk.ac.cam.acr31.sound.AudioSequence;
import uk.ac.cam.acr31.sound.SineWaveSound;

public class SoundOfLife {

	private World mWorld;

	public SoundOfLife(World w) {
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

		ArrayWorld world = new ArrayWorld(args[0]);
		float length = Float.parseFloat(args[1]);
		int number = Integer.parseInt(args[2]);
		String fileName = args[3];

		OutputStream fileOutputStram = new FileOutputStream(new File(fileName));
		AudioSequence soundFile = new AudioSequence(length);

		for (int i = 0; i < number; ++i) {
			SineWaveSound sound = new SineWaveSound(world.getNumberOfLife() * 10, 20);
			world.nextGeneration();
			System.out.println(world.getNumberOfLife());
			soundFile.advance();
			soundFile.addSound(sound);
		}

		soundFile.write(fileOutputStram);

	}

}