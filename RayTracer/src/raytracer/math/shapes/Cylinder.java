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
	public Matrix rot1, rot2; 
	public float radius, h;
	
	public Cylinder(UVTexture texture, SurfaceProperties surface, Vector p1, Vector p2, float r) {
		super(texture, surface);
			
		translation = p1.mul(-1);
		Vector p2Translated = p2.add(translation);
		
		float projectedP2Length = (float)Math.sqrt(p2Translated.x*p2Translated.x + p2Translated.y*p2Translated.y);
		float zRot = (float)Math.acos(p2Translated.x/projectedP2Length);
		rot1 = Matrix.getZRotationMatrix(-zRot);
		
		Vector p2Transformed1 = p2Translated.mul(rot1);
		float yRot = (float)Math.acos(p2Transformed1.z/p2Transformed1.length());
		rot2 = Matrix.getYRotationMatrix(-yRot);
		
		h = p2Translated.length();
		this.radius = r;
	}

	@Override
	public Intersect getIntersect(Ray r) {
		Intersect intersect = new Intersect(this);
		
		Ray rTransformed = new Ray(r.ori.add(translation).mul(rot1).mul(rot2), r.dir.mul(rot1).mul(rot2));
		
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
			
			// do surface normal
			
		}
		
		
		
	}

	@Override
	public float[] toTexCoords(Vector surfacePos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		
		new Cylinder(null, null, new Vector(1, 2, 3), new Vector(4, 5, 6), 2);
		
	}
	
}
