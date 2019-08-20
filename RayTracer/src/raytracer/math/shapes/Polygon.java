package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.rendering.SurfaceProperties;
import raytracer.rendering.UVTexture;

public class Polygon extends Shape{
	
	public Vector[] vertices; 
	
	public Vector normal;
	
	public Polygon(UVTexture texture, SurfaceProperties surface, Vector v1, Vector v2, Vector v3, Vector... vertices) {
		super(texture, surface);
		
		normal = v1.sub(v2).cross(v1.sub(v3));
		
		this.vertices = new Vector[3 + vertices.length];
		
		vertices[0] = v1;
		vertices[1] = v2;
		vertices[2] = v3;
		
		for(int i = 3; i < this.vertices.length; i++) {
			this.vertices[i] = vertices[i - 3]; 
		}
		
	}

	@Override
	public Intersect getIntersect(Ray r) {
		
		Intersect intersect = new Intersect(this);

		float t = vertices[0].sub(r.ori).dot(normal) / r.dir.dot(normal);
		Vector P = r.ori.add(r.dir.mul(t));
		
		if(normal.x > normal.y && normal.x > normal.z) {
			// project the x axis away
			
			
			
			
		}else if(normal.y > normal.x && normal.y > normal.z) {
			// project the y axis away
			
			
		}else {
			// project the z axis away
			
			
		}
		
		
	}

	@Override
	public float[] toTexCoords(Vector surfacePos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
