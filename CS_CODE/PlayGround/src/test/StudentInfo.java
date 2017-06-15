package test;

public class StudentInfo implements Comparable<StudentInfo> {
	private String name; // student name
	protected int nCompleted = 0; // Number of assignments completed

	public StudentInfo(String n) {
		name = n;
	}

	public int getCompleted() {
		return nCompleted;
	}

	public void setCompleted(int n) {
		nCompleted = n;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(StudentInfo s) {
		int c = (s.nCompleted - nCompleted);
		if (c == 0)
			return name.compareTo(s.name);
		return c;
	}

	@Override
	public boolean equals(Object o) {
		StudentInfo s = (StudentInfo) o;
		return (nCompleted == s.nCompleted) && (name.equals(s.name));
	}
}