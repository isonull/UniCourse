package S47B;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ReadAndSort {
	public static void main(String args[]) throws Exception {
		try {
			Reader r = new FileReader("D:/Java_Workspace/OOPSV/src/S47B/test.txt");
			BufferedReader b = new BufferedReader(r);
			String firstLine = b.readLine();
			String secondLine = b.readLine();
			String[] firsts = firstLine.split(",");
			String[] seconds = secondLine.split(",");
			if (firsts.length != seconds.length)
				throw new Exception("The data is not in pair!");
			List<IntPair> data = new LinkedList<IntPair>();
			for (int i = 0; i != firsts.length; ++i) {
				data.add(new IntPair(Integer.parseInt(firsts[i]), Integer.parseInt(seconds[i])));
			}
			Collections.sort(data);
			for (IntPair pair : data) {
				System.out.print(pair.first() + "  ");
				System.out.println(pair.second());
			}
			b.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception happens when reading file.");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Parsing to int failed.");
		}
	}
}
