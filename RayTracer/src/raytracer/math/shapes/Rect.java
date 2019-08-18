package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.rendering.UVTexture;

public class Rect extends Shape{

	public Vector corner1, corner2, normal;
	
	public Rect(UVTexture texture, Vector corner1, Vector corner2, Vector normal) {
		super(texture);
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.normal = normal;
		
	
		
	}

	@Override
	public Intersect getIntersect(Ray r) {
		
		Intersect intersect = new Intersect(this);
		
		
		
		//TODO
		return null;
	}

	@Override
	public float[] toTexCoords(Vector surfacePos) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
