package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Matrix;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
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
		float zRot = (float)Math.acos(p2Translated.x/projectedP2Length);
		Matrix rot1 = Matrix.getZRotationMatrix(-zRot);
		
		Vector p2Transformed1 = p2Translated.mul(rot1);
		float yRot = (float)Math.acos(p2Transformed1.z/p2Transformed1.length());
		Matrix rot2 = Matrix.getYRotationMatrix(-yRot);
		
		h = p2Translated.length();
		radius = r;
		rotation = rot2.mul(rot1);
		invRotation = rotation.inverse();
		
		
		System.out.println(p2);
		
		System.out.println(translation);
		System.out.println(rotation.toGGB());
		System.out.println(invRotation.toGGB());
		
	}

	@Override
	public Intersect getIntersect(Ray r) {
		Intersect intersect = new Intersect(this);
		
		Ray rTransformed = new Ray(r.ori.add(translation).mul(rotation), r.dir.mul(rotation));
		
		// r*r=x*x+y*y
		// P = A + t * B
		// r*r = (A.x + tB.x)*(A.x + tB.x) + (A.y + tB.y)*(A.y + tB.y)
		// = A.x*A.x + 2*A.x*B.x*t + tB.x*tB.x + A.y*A.y + 2*A.y*B.y*t + tB.x*tB.x
		// = (B.x^2 + B.y^2)t^2 + (2*A.x*B.x + 2*A.y*B.y)t + A.x^2 + A.y^2
		// = (B.x^2 + B.y^2)t^2 + 2(A.x*B.x + A.y*B.y)t + A.x^2 + A.y^2
		// solve quadratic for t
		
		float a = rTransformed.dir.x * rTransformed.dir.x + rTransformed.dir.y * rTransformed.dir.y;
		float b = 2 * (rTransformed.ori.x * rTransformed.dir.x + rTransformed.ori.y * rTransformed.dir.y);
		float c = rTransformed.ori.x * rTransformed.ori.x + rTransformed.ori.y * rTransformed.ori.y - radius * radius;

		float disc = b*b - 4*a*c;
		
		if(disc <= 0) {
			return intersect;
		}
		
		float t1 = (-b + (float)Math.sqrt(disc))/(2*a);
		float t2 = (-b - (float)Math.sqrt(disc))/(2*a);
		
		boolean t1Hit = t1 > 0 && rTransformed.ori.z + t1 * rTransformed.dir.z < h;
		boolean t2Hit = t2 > 0 && rTransformed.ori.z + t2 * rTransformed.dir.z < h;
		
		if(!t1Hit && !t2Hit) {
			return intersect;
		}else {
			intersect.dist = t1 < t2 && t1Hit ? t1 : t2;
			
			intersect.normal = rTransformed.ori.add(rTransformed.dir.mul(intersect.dist));
			intersect.normal.z = 0;
			intersect.normal.div(radius);
			intersect.normal.mul(invRotation);
			
			intersect.normal = new Vector(0, 1, 0);
			
			if(t1Hit ^ t2Hit) {
				intersect.normal.mul(-1);
			}
			
			return intersect;
		}
		
	}

	@Override
	public float[] toTexCoords(Vector surfacePos) {
		float[] tex = new float[2];
		
		Vector spTransformed = surfacePos.add(translation);
		spTransformed = spTransformed.mul(rotation);
		
		tex[0] = 0;
		tex[1] = spTransformed.z/h;
		
		return tex;
	}
	
	public static void main(String[] args) {
		
		new Cylinder(null, null, new Vector(1, 2, 3), new Vector(4, 5, 6), 2);
		
	}
	
}
