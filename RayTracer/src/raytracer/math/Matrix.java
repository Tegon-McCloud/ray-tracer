package raytracer.math;

public class Matrix {
	
	float m00, m01, m02, 
		  m10, m11, m12,
		  m20, m21, m22;

	public Matrix(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
	}
	
	public Matrix mul(Matrix m) {
		
		return new Matrix(
				m00 * m.m00 + m01 * m.m10 + m02 * m.m20, m00 * m.m01 + m01 * m.m11 + m02 * m.m21, m00 * m.m02 + m01 * m.m12 + m02 * m.m22,
				m10 * m.m00 + m11 * m.m10 + m12 * m.m20, m10 * m.m01 + m11 * m.m11 + m12 * m.m21, m10 * m.m02 + m11 * m.m12 + m12 * m.m22,
				m20 * m.m00 + m21 * m.m10 + m22 * m.m20, m20 * m.m01 + m21 * m.m11 + m22 * m.m21, m20 * m.m02 + m21 * m.m12 + m22 * m.m22
				);
		
	}
	
	public float determinant() {
		// Sarrus rule
		return m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21 - (m20 * m11 * m02 + m21 * m12 * m00 + m22 * m10 * m01);
	}
	
	public Matrix transposed() {
		return new Matrix(
				m00, m10, m20,
				m01, m11, m21,
				m02, m12, m22);
	}
	
	public Matrix adjugate() {
		Matrix mT = transposed();
		
		return new Matrix(
				(mT.m11 * mT.m22 - mT.m12 * mT.m21), -(mT.m10 * mT.m22 - mT.m12 * mT.m20), (mT.m10 * mT.m21 - mT.m11 * mT.m20),
				-(mT.m01 * mT.m22 - mT.m02 * mT.m21), (mT.m00 * mT.m22 - mT.m02 * mT.m20), -(mT.m00 * mT.m21 - mT.m01 * mT.m20),
				(mT.m01 * mT.m12 - mT.m02 * mT.m11), -(mT.m00 * mT.m12 - mT.m02 * mT.m10), (mT.m00 * mT.m11 - mT.m01 * mT.m10)
				);
	}
	
	public Matrix inverse() {
		Matrix adjm = adjugate();
		float det = determinant();
		
		return new Matrix(
				adjm.m00/det, adjm.m01/det, adjm.m02/det,
				adjm.m10/det, adjm.m11/det, adjm.m12/det, 
				adjm.m20/det, adjm.m21/det, adjm.m22/det
				);
		
	}
	
	@Override
	public String toString() {
		return "|\t" + m00 + "\t" + m01 + "\t" + m02 + "\t|\n"
			 + "|\t" + m10 + "\t" + m11 + "\t" + m12 + "\t|\n"
			 + "|\t" + m20 + "\t" + m21 + "\t" + m22 + "\t|";
	}
	
	public String toGGB() {
		return "{" + "{" + m00 + ", " + m01 + ", " + m02 + "}," + "{" + m10 + ", " + m11 + ", " + m12 + "}," + "{" + m20 + ", " + m21 + ", " + m22 + "}" + "}";
	}
	
	public static Matrix getXRotationMatrix(float theta) {
		
		float sin = (float)Math.sin(theta);
		float cos = (float)Math.cos(theta);
		
		return new Matrix(
				1, 0, 0,
				0, cos, -sin,
				0, sin, cos
				);
	}
	
	public static Matrix getYRotationMatrix(float theta) {
		
		float sin = (float)Math.sin(theta);
		float cos = (float)Math.cos(theta);
		
		return new Matrix(
				cos, 0, sin,
				0, 1, 0,
				-sin, 0, cos
				);
	}
	
	public static Matrix getZRotationMatrix(float theta) {
		
		float sin = (float)Math.sin(theta);
		float cos = (float)Math.cos(theta);
		
		return new Matrix(
				cos, -sin, 0,
				sin, cos, 0,
				0, 0, 1
				);
	}
	
}
