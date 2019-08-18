package raytracer.math;

public abstract class Shape {
	
	public Vector color;
	
	public Shape(Vector color) {
		this.color = color;
	}

	public abstract Intersect getIntersect(Ray r);
	
}
