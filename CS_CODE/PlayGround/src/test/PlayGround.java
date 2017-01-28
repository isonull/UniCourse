package test;

public class PlayGround {

	public int x = 6;

	// public boolean equals(PlayGround p2) {
	// return this.x == p2.x;
	// }
	public static void main(String args[]) {
		int[] aa = new int[10];
		PlayGround a = new PlayGround();
		double b = Double.NaN;
		double nan = 0.0d / 0.0;
		byte by = (byte) (1 << 100);
		System.out.println(by);
		System.out.println(Integer.MIN_VALUE);
		System.out.println(b == b);
		System.out.println("three" + 3);
		System.out.println(Double.NaN);
		PlayGround p1 = new PlayGround();
		PlayGround p2 = new PlayGround();
		Object o1 = (Object) p1;
		Object o2 = (Object) p2;
		Integer i1 = 300;
		Integer i2 = 300;
		System.out.println(i1 == i2);
		System.out.println(i1.equals(i2));
		System.out.println(p1.equals(p2));
		System.out.println(o1.equals(o2));
	}
}
