package raytracer.math;

public class Vector {
	
	public float x, y, z;
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Vector v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector sub(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}
	
	public Vector mul(float f) {
		return new Vector(x*f, y*f, z*f);
	}
	
	public Vector mul(Vector v) {
		return new Vector(x * v.x, y * v.y, z * v.z);
	}
	
	public Vector div(float f) {
		return new Vector(x/f, y/f, z/f);
	}
	
	public float dot(Vector v) {
		return x*v.x + y*v.y + z*v.z;
	}
	
	public Vector cross(Vector v) {
		return new Vector(y * v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
	}
	
	public Vector normalized() {
		float length = length();
		return new Vector(x/length, y/length, z/length);
	}
	
	public float length() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector mul(Matrix m) {
		return new Vector(m.m00 * x  + m.m01 * y + m.m02 * z, m.m10 * x  + m.m11 * y + m.m12 * z, m.m20 * x  + m.m21 * y + m.m22 * z);
	}
	
	@Override
	public String toString() {
		return "<" + x + ", " + y + ", " + z + ">";
	}
	
	public String toGGB() {
		return "Vector((" + x + ", " + y + ", " + z +"))";
	}
	
	
	public String toPointGGB() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	public static void main(String[] args) {
		Vector v1 = new Vector(3, -4, 0);
		Vector v2 = new Vector(1, 5, 9);
		
		System.out.println(v1.cross(v2));
		
	}
	
}
