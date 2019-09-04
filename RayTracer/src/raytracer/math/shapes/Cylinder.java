package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Matrix;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.rendering.Checkerboard;
import raytracer.rendering.SurfaceProperties;
import raytracer.rendering.UVTexture;

public class Cylinder extends Shape {
	
	public Vector translation;
	public Matrix rotation, invRotation;
	public float radius, h;
	
	public Cylinder(UVTexture texture, SurfaceProperties surface, Vector p1, Vector p2, float r) {
		super(texture, surface);
		
		translation = p1.mul(-1);
		Vector p2Translated = p2.add(translation);
		
		float projectedP2Length = (float)Math.sqrt(p2Translated.x*p2Translated.x + p2Translated.y*p2Translated.y);
		Vector iNew = new Vector(p2Translated.x/projectedP2Length, p2Translated.y/projectedP2Length, 0);
		Vector kNew = new Vector(0, 0, 1);
		Vector jNew = iNew.cross(kNew);
		
		Matrix rot1 = new Matrix(iNew.x, jNew.x, kNew.x,
								 iNew.y, jNew.y, kNew.y,
								 iNew.z, jNew.z, kNew.z);
		
		Vector p2ZRotated = p2Translated.mul(rot1);
		h = p2Translated.length();
		
		kNew = new Vector(p2ZRotated.x/h, 0, p2ZRotated.z/h);
		jNew = new Vector(0, 1, 0);
		iNew = kNew.cross(jNew);
		
		Matrix rot2 = new Matrix(iNew.x, jNew.x, kNew.x,
				 				 iNew.y, jNew.y, kNew.y,
				 				 iNew.z, jNew.z, kNew.z);
		
		rotation = rot2.mul(rot1);
		invRotation = rotation.inverse();
		
		radius = r;
		
		System.out.println(rotation.toGGB());
		System.out.println(invRotation.toGGB());

	}

	@Override
	public Intersect getIntersect(Ray r) {
		Intersect intersect = new Intersect(this);
		
		// transformed ray
		Ray rT = new Ray(r.ori.add(translation).mul(rotation), r.dir.mul(rotation));
		
		// r*r=x*x+y*y
		// P = A + t * B
		// r*r = (A.x + tB.x)*(A.x + tB.x) + (A.y + tB.y)*(A.y + tB.y)
		// = A.x*A.x + 2*A.x*B.x*t + tB.x*tB.x + A.y*A.y + 2*A.y*B.y*t + tB.x*tB.x
		// = (B.x^2 + B.y^2)t^2 + (2*A.x*B.x + 2*A.y*B.y)t + A.x^2 + A.y^2
		// = (B.x^2 + B.y^2)t^2 + 2(A.x*B.x + A.y*B.y)t + A.x^2 + A.y^2
		// solve quadratic for t
		
		float a = rT.dir.x * rT.dir.x + rT.dir.y * rT.dir.y;
		float b = 2 * (rT.ori.x * rT.dir.x + rT.ori.y * rT.dir.y);
		float c = rT.ori.x * rT.ori.x + rT.ori.y * rT.ori.y - radius * radius;

		float disc = b*b - 4*a*c;
		
		if(disc <= 0) {
			return intersect;
		}
		
		float t1 = (-b + (float)Math.sqrt(disc))/(2*a);
		float t2 = (-b - (float)Math.sqrt(disc))/(2*a);
		
		if(t2 < t1) {
			float swap = t1;
			t1 = t2;
			t2 = swap;
		}
		
		float z1 = rT.ori.z + t1 * rT.dir.z;
		float z2 = rT.ori.z + t2 * rT.dir.z;
		
		boolean t1Hit = t1 > 0 && 0 < z1 && z1 < h;
		boolean t2Hit = t2 > 0 && 0 < z2 && z2 < h;
		
		if(!t1Hit && !t2Hit) {
			return intersect;
		}else {
			intersect.dist = t1Hit ? t1 : t2;
			
			intersect.normal = rT.ori.add(rT.dir.mul(intersect.dist));
			intersect.normal.z = 0;
			intersect.normal = intersect.normal.div(radius).mul(invRotation);
			
			if(!t1Hit) {
				intersect.normal = intersect.normal.mul(-1);
			}
			
			return intersect;
		}
		
	}

	@Override
	public float[] toTexCoords(Vector surfacePos) {
		float[] tex = new float[2];
		
		Vector spTransformed = surfacePos.add(translation).mul(rotation);
		
		//System.out.println(spTransformed.toGGB());
		
		float angle = (float)Math.acos(spTransformed.x/radius);
		
		if(spTransformed.y < 0) {
			angle = 2*(float)Math.PI - angle;
		}
		
		//System.out.println(angle);
		
		tex[0] = angle/(2*(float)Math.PI);
		
		
		tex[1] = spTransformed.z/h;
		
		return tex;
	}
	
	public static void main(String[] args) {
		
		new Cylinder(
				new Checkerboard(new Vector(0.5f, 0.5f, 0), new Vector(0, 0, 0.5f), 8),
				new SurfaceProperties(0.5f, 0.7f, 0.5f, 100f, true, true), 
				new Vector(-1, -1, 1), new Vector(1, -2, 0), 1);
		
	}
	
}
