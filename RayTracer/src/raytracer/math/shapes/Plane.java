package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.rendering.SurfaceProperties;
import raytracer.rendering.UVTexture;

public class Plane extends Shape{


	public Vector p1, p2, p3;
	private Vector normal;

	public Plane(UVTexture texture, SurfaceProperties surface, Vector p1, Vector p2, Vector p3) {
		super(texture, surface);
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		
		normal = p2.sub(p1).cross(p3.sub(p1)).normalized();
	}



	@Override
	public Intersect getIntersect(Ray r) {

		Intersect intersect = new Intersect(this);

		float nDotDir = r.dir.dot(normal);
		
		if(Math.abs(nDotDir) < 0.0001) {
			return intersect;
		}
		
		float t = p1.sub(r.ori).dot(normal) / nDotDir;

		if(t < 0) {
			return intersect;
		}

		Vector P = r.ori.add(r.dir.mul(t));

		intersect.dist = t;
		
		if(r.dir.dot(normal) > 0) {
			intersect.normal = normal.mul(-1);
		}else {
			intersect.normal = new Vector(normal);
		}

		return intersect;
	}



	@Override
	public float[] toTexCoords(Vector surfacePos) {
		float[] texCoords = new float[2];
		texCoords[0] = surfacePos.x;
		texCoords[1] = surfacePos.z;
		return texCoords;
	}




}
