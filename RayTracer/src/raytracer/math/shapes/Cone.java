package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.rendering.SurfaceProperties;
import raytracer.rendering.UVTexture;

public class Cone extends Shape {

	public Cone(UVTexture texture, SurfaceProperties surface, Vector p0, Vector p1, float r) {
		super(texture, surface);
		
		
		
	}

	@Override
	public Intersect getIntersect(Ray r) {
		Intersect intersect = new Intersect(this);
		return null;
	}

	@Override
	public float[] toTexCoords(Vector surfacePos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
