package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.rendering.UVTexture;

public class Sphere extends Shape{
	
	public float radius;
	public Vector pos;
	
	public Sphere(UVTexture texture, Vector pos, float radius) {
		super(texture);
		
		this.pos = pos;
		this.radius = radius;
	}
	
	/*
	ray:
	A = ori
	B = dir
	P = A + t * B

	sphere:
	P = point on sphere
	C = pos
	R = radius
	R^2 = (P - C)*(P - C)
	
	Combined:
	R^2 = (A + t * B - C)*(A + t * B - C)

	R^2 = (A - C + tB)*(A - C + tB)

	R^2 = ((A-C) + tB)*((A-C) + tB)
	
	R^2 = tB*tB + 2tB*(A-C) + (A-C)*(A-C)
	
	R^2 = (B*B)t^2 + (2 * B*(A-C))t + (A-C)*(A-C)
	
	0 = (B*B)t^2 + (2 * B*(A-C))t + (A-C)*(A-C) - R^2
	
	solve quadratic equation for t
	
	*/
	@Override
	public Intersect getIntersect(Ray r) {
		
		Intersect intersect = new Intersect(this);
		
		Vector AminusC = r.ori.sub(pos);
		float a = r.dir.dot(r.dir);
		float b = 2 * r.dir.dot(AminusC);
		float c = AminusC.dot(AminusC) - radius*radius;
		
		float disc = b*b - 4*a*c;
		
		if(disc <= 0) {
			return intersect;
		}
		
		float t1 = (-b + (float)Math.sqrt(disc))/(2*a);
		float t2 = (-b - (float)Math.sqrt(disc))/(2*a);
		
		boolean t1Hit = t1 > 0;
		boolean t2Hit = t2 > 0;
		
		if(!t1Hit && !t2Hit) {
			return intersect;
		}else {
			intersect.dist = t1 < t2 && t1Hit ? t1 : t2;
			
			Vector P = r.ori.add(r.dir.mul(intersect.dist));
			intersect.normal = P.sub(pos);
			
			if(t1Hit ^ t2Hit) {
				intersect.normal = intersect.normal.mul(-1);
			}
			
			intersect.normal = intersect.normal.div(radius); // normalize
			return intersect;
			
		}
		
	}

	@Override
	public float[] toTexCoords(Vector surfacePos) {
		
		Vector n = surfacePos.sub(pos);
		float[] coords = new float[2];
		coords[0] = (float)(0.5 + Math.atan2(n.x, n.z)/(2*Math.PI));
		coords[1] = (float)(0.5 - Math.asin(n.y)/Math.PI);
				
		return coords;
	}
	
	
	
}
