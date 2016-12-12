package S11B;

public class Vector2Dp {
	private float a;
	private float b;
	
	public float geta(){
		return a;
	}
	
	public float getb(){
		return b;
	}
	
	public Vector2Dp(float x, float y) {
		a=x;
		b=y;
	}
	
	public void add(Vector2Dp v) {
		this.a = this.a + v.geta();
		this.b = this.b + v.getb();
	}
	
	public Vector2Dp add1(Vector2Dp v) {
		return new Vector2Dp(this.a+v.geta(),this.b+v.getb());
	}
	
	public Vector2Dp add(Vector2Dp v1, Vector2Dp v2) {
		return new Vector2Dp(v1.geta()+v2.geta(),v1.getb()+v2.getb());
	}
	
	public static Vector2Dp add1(Vector2Dp v1, Vector2Dp v2) {
		return new Vector2Dp(v1.geta()+v2.geta(),v1.getb()+v2.getb());
	}
	
}
