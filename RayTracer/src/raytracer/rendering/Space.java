package raytracer.rendering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import raytracer.math.DistantLight;
import raytracer.math.Intersect;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Vector;
import raytracer.math.shapes.Sphere;
import raytracer.math.shapes.Triangle;

public class Space {

	public static final float BIAS = 0.001f;
	
	
	public Vector background = new Vector(0.2f, 0.6f, 0.9f); 
	public Vector ia = new Vector(0.5f, 0.5f, 0.5f);

	private java.util.Vector<Shape> shapes = new java.util.Vector<Shape>();
	private java.util.Vector<DistantLight> distantLights = new java.util.Vector<DistantLight>();


	public Space() {

	}

	public void add(Shape s) {
		shapes.add(s);
	}

	public void add(DistantLight dl) {
		distantLights.add(dl);
	}


	public Vector castRay(Ray r, int depth) {

		Intersect nearestIntersect = trace(r);

		if(nearestIntersect == null) {
			return background;
		}

		return phongColor(r, nearestIntersect);
	}

	public Intersect trace(Ray r) {

		Intersect intersect = null;

		for(int i = 0; i < shapes.size(); i++) {
			Intersect testIntersect = shapes.get(i).getIntersect(r);

			if(Float.isFinite(testIntersect.dist)) {
				if(intersect == null || testIntersect.dist < intersect.dist) {
					intersect = testIntersect;
				}
			}
		}
		
		return intersect;

	}

	public Vector phongColor(Ray r, Intersect intersect) {
		
		Vector P = r.ori.add(r.dir.mul(intersect.dist));
		Vector ambient = ia.mul(intersect.shape.getSurfaceProperties().ka).mul(intersect.shape.getColor(P));
		
		Vector diffuse = new Vector(0, 0, 0);
		
		for(int i = 0; i < distantLights.size(); i++) {
			
			Ray shadowRay = new Ray(P.add(intersect.normal.mul(BIAS)), distantLights.get(i).dir.mul(-1));
			System.out.println(shadowRay.ori + " " + shadowRay.dir);
			
			Intersect shadowIntersect = trace(shadowRay);
			
			System.out.println(shadowIntersect.shape);
			
			if(shadowIntersect == null) {
				
				float nDotlDir = intersect.normal.dot(intersect.normal);
				Vector diffuseIncoming = distantLights.get(i).id.mul(nDotlDir * intersect.shape.getSurfaceProperties().kd);
				Vector objectColor = intersect.shape.getColor(P);
				
				diffuse = diffuse.add(new Vector(diffuseIncoming.x * objectColor.x, diffuseIncoming.y * objectColor.y, diffuseIncoming.z * objectColor.z));
			}
			
		}
		
		
		return ambient.add(diffuse);
		
	}


	public static void main(String[] args) throws IOException {

		Space s = new Space();
		//Camera c = new Camera(512, 512, new Vector(0, 0, 0), (float)Math.PI/2, (float)Math.PI/2, (float)Math.PI/4, (float)-Math.PI/4);
		
		//s.add(new Sphere(new ImageTexture(new File("D:\\test\\texture.png")), new SurfaceProperties(0, 0, 0, 0, 0, 0), new Vector(2, 2, -2), 1));
		
		Camera c = new Camera(512, 512, new Vector(0, -3, 6), (float)Math.PI/2, (float)Math.PI/2, (float)Math.PI/8, 0);
		
//		s.add(new Sphere(
//				new Checkerboard(new Vector(0.5f, 0.5f, 0), new Vector(0, 0, 0.5f), 8),
//				new SurfaceProperties(0.5f, 0.5f, 0.5f, 0.5f, 0, 0),
//				new Vector(0, -1.5f, 0),
//				1));

		s.add(new Triangle(
				new UVTexture() {
					@Override
					public Vector getColor(float texX, float texY) {
						return new Vector(0.5f, 0.5f, 0.5f);
					}
				}, 
				new SurfaceProperties(0.5f, 0.5f, 0.5f, 0.5f, 0, 0),
				new Vector(3, 0, 3), new Vector(-3, 0, 3), new Vector(-3, 0, -3)));

		s.add(new Triangle(
				new UVTexture() {
					@Override
					public Vector getColor(float texX, float texY) {
						return new Vector(0.5f, 0.5f, 0.5f);
					}
				}, 
				new SurfaceProperties(0.5f, 0.5f, 0.5f, 0.5f, 0, 0),
				new Vector(-3, 0, -3), new Vector(3, 0, -3), new Vector(3, 0, 3)));

		s.add(new DistantLight(new Vector(0, 1, 0).normalized(), new Vector(0.8f, 0.8f, 0.8f), new Vector(0.8f, 0.8f, 0.8f)));

		BufferedImage bi = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i < c.getWidth(); i++) {
			for(int j = 0; j < c.getHeight(); j++) {

				Vector vecColor = s.castRay(c.getRay(i, j), 0);
				Color color = new Color(vecColor.x, vecColor.y, vecColor.z);
				bi.setRGB(i, j, color.getRGB());
			}
		}

		try {
			ImageIO.write(bi, "png", new File("D:\\test\\hej.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
