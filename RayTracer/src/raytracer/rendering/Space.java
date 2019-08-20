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

	public Vector background = new Vector(0.2f, 0.6f, 0.9f); 
	public Vector ia = new Vector(0.1f, 0.1f, 0.1f);

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

		return nearestIntersect.shape.getColor(r.ori.add(r.dir.mul(nearestIntersect.dist)));
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
		
		Vector color = ia.mul(intersect.shape.getSurfaceProperties().ka);
		
		
	}


	public static void main(String[] args) throws IOException {

		Space s = new Space();
		Camera c = new Camera(512, 512, new Vector(0, -3, 6), (float)Math.PI/2, (float)Math.PI/2, (float)Math.PI/8, 0);

		s.add(new Sphere(
				new Checkerboard(new Vector(0.5f, 0.5f, 0), new Vector(0, 0, 0.5f), 8),
				new SurfaceProperties(0.5f, 0.5f, 0.5f, 0.5f, 0, 0),
				new Vector(0, -1.5f, 0),
				1));




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

		s.add(new DistantLight(new Vector(0, -1, -1).normalized(), new Vector(0.8f, 0.8f, 0.8f), new Vector(0.8f, 0.8f, 0.8f)));

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
