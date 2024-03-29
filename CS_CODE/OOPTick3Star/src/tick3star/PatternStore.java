package tick3star;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PatternStore {

	java.util.Comparator<Pattern> authorComparator = new Comparator<Pattern>() {
		public int compare(Pattern p1, Pattern p2) {
			return (p1.getAuthor()).compareTo(p2.getAuthor());
		}
	};

	private List<Pattern> mPatterns = new LinkedList<>();
	private Map<String, List<Pattern>> mMapAuths = new HashMap<>();
	private Map<String, Pattern> mMapName = new HashMap<>();

	public PatternStore(String source) throws IOException {
		if (source.startsWith("http://")) {
			loadFromURL(source);
		} else {
			loadFromDisk(source);
		}
	}

	public PatternStore(Reader source) throws IOException {
		load(source);
	}

	private void load(Reader r) throws IOException {
		// TODO: read each line from the reader and print it to the screen
		BufferedReader b = new BufferedReader(r);
		String line = b.readLine();

		while (line != null) {
			System.out.println(line);
			try {
				Pattern p = new Pattern(line);
				mPatterns.add(p);
				if (!mMapAuths.containsKey(p.getAuthor())) {
					mMapAuths.put(p.getAuthor(), new LinkedList<Pattern>());
				}
				mMapAuths.get(p.getAuthor()).add(p);
				mMapName.put(p.getName(), p);

			} catch (PatternFormatException e) {
				System.out.println(line + " is in wrong format.");
			}
			line = b.readLine();
		}
	}

	private void loadFromURL(String url) throws IOException {
		// TODO: Create a Reader for the URL and then call load on it
		URL destination = new URL(url);
		URLConnection connection = destination.openConnection();
		Reader r = new java.io.InputStreamReader(connection.getInputStream());
		load(r);
	}

	private void loadFromDisk(String filename) throws IOException {
		// TODO: Create a Reader for the file and then call load on it
		Reader r = new FileReader(filename);
		load(r);
	}

	public List<Pattern> getPatternsNameSorted() {
		// TODO: Get a list of all patterns sorted by name
		Collections.sort(mPatterns);
		List<Pattern> p = new LinkedList<Pattern>(mPatterns);
		return p;
	}

	public List<Pattern> getPatternsAuthorSorted() {
		// TODO: Get a list of all patterns sorted by author then name
		List<Pattern> l = new LinkedList<>();

		for (Entry<String, List<Pattern>> entry : mMapAuths.entrySet()) {
			for (Pattern p : entry.getValue()) {
				l.add(p);
			}
		}

		Collections.sort(l);
		Collections.sort(l, authorComparator);
		return l;

	}

	public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
		// TODO: return a list of patterns from a particular author sorted by
		// name
		if (!mMapAuths.containsKey(author))
			throw new PatternNotFound(author + " not found.");
		List<Pattern> l = new LinkedList<>(mMapAuths.get(author));
		Collections.sort(l);
		return l;
	}

	public Pattern getPatternByName(String name) throws PatternNotFound {
		// TODO: Get a particular pattern by name
		if (!mMapName.containsKey(name))
			throw new PatternNotFound(name + " not found.");
		return mMapName.get(name);
	}

	public List<String> getPatternAuthors() {
		// TODO: Get a sorted list of all pattern authors in the store
		List<String> l = new LinkedList<>(mMapAuths.keySet());
		Collections.sort(l);
		return l;
	}

	public List<String> getPatternNames() {
		// TODO: Get a list of all pattern names in the store,
		// sorted by name
		List<String> l = new LinkedList<>();
		for (Pattern p : mPatterns) {
			l.add(p.getName());
		}
		Collections.sort(l);
		return l;
	}

	public static void main(String args[]) throws IOException, PatternNotFound {
		PatternStore p = new PatternStore(args[0]);
	}

}
