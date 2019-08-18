package raytracer.math;

public class Intersect {
	
	public float dist = Float.POSITIVE_INFINITY;
	public Vector normal;
	public Shape shape;
	
	public Intersect(Shape s) {
		shape = s;
	}
	
}
