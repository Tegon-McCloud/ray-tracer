package raytracer.math.shapes;

import raytracer.math.Intersect;
import raytracer.math.Matrix;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.rendering.SurfaceProperties;
import raytracer.rendering.UVTexture;

public class Cone extends Shape {
	
	public Vector translation;
	public Matrix rotation, invRotation;
	public float radius, h; // radius is radius at z = 1 in transformed space
	
	public Cone(UVTexture texture, SurfaceProperties surface, Vector p1, Vector p2, float r) {
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
		
		radius = r/h;

	}

	@Override
	public Intersect getIntersect(Ray r) {
		Intersect intersect = new Intersect(this);
		
		final float r2 = radius*radius;
		
		Ray rT = new Ray(r.ori.add(translation).mul(rotation), r.dir.mul(rotation));
		
		float a = rT.dir.x*rT.dir.x + rT.dir.y*rT.dir.y - r2*rT.dir.z*rT.dir.z;
		float b = 2*(rT.ori.x*rT.dir.x + rT.ori.y*rT.dir.y - r2*rT.ori.z*rT.dir.z);
		float c = rT.ori.x*rT.ori.x + rT.ori.y*rT.ori.y - r2*rT.ori.z*rT.ori.z;
		
		float disc = b*b - 4*a*c;
		
		if(disc < 0) {
			return intersect;
		}
		
		float sqrtDisc = (float)Math.sqrt(disc);
		
		float t1 = (-b + sqrtDisc)/(2*a);
		float t2 = (-b - sqrtDisc)/(2*a);
		
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
			Vector spTransformed = rT.ori.add(rT.dir.mul(intersect.dist));
			
			float theta =  (float)Math.acos(spTransformed.x/Math.sqrt(spTransformed.x*spTransformed.x + spTransformed.y*spTransformed.y));
			if(spTransformed.y < 0) {
				theta = 2*(float)Math.PI - theta;
			}
			
			float h2 = h*h;
			float sidelength = (float)Math.sqrt(r2*h2 + h2);
			float hOverSide = h/sidelength;
			
			intersect.normal = new Vector((float)Math.cos(theta) * hOverSide, (float)Math.sin(theta) * hOverSide, -radius*hOverSide);
			intersect.normal = intersect.normal.mul(invRotation);
			
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
		
		float angle = (float)Math.acos(spTransformed.x/(radius * spTransformed.z));
		
		if(spTransformed.y < 0) {
			angle = 2*(float)Math.PI - angle;
		}
		
		//System.out.println(angle);
		
		tex[0] = angle/(2*(float)Math.PI);
		
		
		tex[1] = spTransformed.z/h;
		
		return tex;
	}
	
	
	
	
}
