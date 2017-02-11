package tick5;

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
		URL destination = new URL(url);
		URLConnection connection = destination.openConnection();
		Reader r = new java.io.InputStreamReader(connection.getInputStream());
		load(r);
	}

	private void loadFromDisk(String filename) throws IOException {
		Reader r = new FileReader(filename);
		load(r);
	}

	public List<Pattern> getPatternsNameSorted() {
		Collections.sort(mPatterns);
		List<Pattern> p = new LinkedList<Pattern>(mPatterns);
		return p;
	}

	public List<Pattern> getPatternsAuthorSorted() {
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
		if (!mMapAuths.containsKey(author))
			throw new PatternNotFound(author + " not found.");
		List<Pattern> l = new LinkedList<>(mMapAuths.get(author));
		Collections.sort(l);
		return l;
	}

	public Pattern getPatternByName(String name) throws PatternNotFound {
		if (!mMapName.containsKey(name))
			throw new PatternNotFound(name + " not found.");
		return mMapName.get(name);
	}

	public List<String> getPatternAuthors() {
		List<String> l = new LinkedList<>(mMapAuths.keySet());
		Collections.sort(l);
		return l;
	}

	public List<String> getPatternNames() {
		List<String> l = new LinkedList<>();
		for (Pattern p : mPatterns) {
			l.add(p.getName());
		}
		Collections.sort(l);
		return l;
	}

}
