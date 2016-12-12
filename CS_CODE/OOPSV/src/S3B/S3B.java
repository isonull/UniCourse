package S3B;

public class S3B {
	public static float[][] creatMatrix(int n) {
		float[][] mat = new float[n][n];
		return mat;
	}
	public static float[][] transpose(float[][] mat) {
		float store;
		int len = mat.length;
		for(int i = 0; i < len; ++i) {
			for(int j = i; j < len; ++j) {
				store = mat[i][j];
				mat[i][j] = mat[j][i];
				mat[j][i] = store;
			}
		}
		return mat;
	}
	
	public static void main(String arg[]) {
		float[][] t = {{1,2,3},{4,5,6},{7,8,9}};
		S3B.transpose(t);
		
	}
}


