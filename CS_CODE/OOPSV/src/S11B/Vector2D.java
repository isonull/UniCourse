package S11B;

public class Vector2D {
	public float a;
	public float b;
	
	public Vector2D() {
		a=0;
		b=0;
	}
	public Vector2D(float x,float y) {
		a = x;
		b = y;
	}

	public static float scalarProduct(Vector2D x, Vector2D y) {
		float t = x.a*y.a+x.b*y.b;
		return t;
	}
	
	public static Vector2D normalise(Vector2D x) {
		float mag = magnitude(x);
		return new Vector2D(x.a/mag,x.b/mag);
	}
	
	public static float magnitude(Vector2D x) {
		float t = (float) Math.sqrt(x.a*x.a+x.b*x.b);
		return t;
	}
	
	public void add(Vector2D v) {
		this.a = this.a + v.a;
		this.b = this.b + v.b;
	}
	
	public Vector2D add1(Vector2D v) {
		return new Vector2D(this.a+v.a,this.b+v.b);
	}
	public Vector2D add(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.a+v2.a,v1.b+v2.b);
	}
	public static Vector2D add1(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.a+v2.a,v1.b+v2.b);
	}
	
	public static void main(String args[]) {
		int i = 1;
		Vector2D t = new Vector2D(i,1);
		++i;
		System.out.print(t.a);
	}
}
