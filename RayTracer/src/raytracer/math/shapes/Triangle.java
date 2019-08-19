package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.rendering.UVTexture;

public class Triangle extends Shape{

	public Vector corner1, corner2, corner3;

	private Vector normal, c1c2, c2c3, c3c1;

	public Triangle(UVTexture texture, Vector corner1, Vector corner2, Vector corner3) {
		super(texture);
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.corner3 = corner3;


		c1c2 = corner2.sub(corner1);
		c2c3 = corner3.sub(corner2);
		c3c1 = corner1.sub(corner3);

		normal = c1c2.cross(c2c3).normalized();
	}

	/*
	ray: P = ori + t * dir

	plane: (P - ori2) * n = 0

	(ori + t * dir - ori2) * n = 0

	ori * n + t * dir * n - ori2 * n = 0

	ori2 * n - ori * n = t * dir * n

	((ori2 - ori) * n)/(dir * n) = t
	 */

	@Override
	public Intersect getIntersect(Ray r) {

		Intersect intersect = new Intersect(this);

		float t = corner1.sub(r.ori).dot(normal) / r.dir.dot(normal);

		Vector P = r.ori.add(r.dir.mul(t));
		
		boolean c1Side = P.sub(corner1).cross(c1c2).dot(normal) > 0;
		boolean c2Side = P.sub(corner2).cross(c2c3).dot(normal) > 0;
		boolean c3Side = P.sub(corner3).cross(c3c1).dot(normal) > 0;
		
		if(c1Side == c2Side && c2Side == c3Side) {
			
			intersect.dist = t;
			
			if(r.dir.dot(normal) > 0) {
				intersect.normal = normal.mul(-1);
			}else {
				intersect.normal = new Vector(normal);
			}
		}

		return intersect;
	}

	@Override
	public float[] toTexCoords(Vector surfacePos) {
		return new float[][]
		
		return new float[2];
	}

}
