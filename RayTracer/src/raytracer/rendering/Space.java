package raytracer.rendering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import raytracer.math.Intersect;
import raytracer.math.Ray;
import raytracer.math.Shape;
import raytracer.math.Sphere;
import raytracer.math.Vector;

public class Space {
	
	public Vector background = new Vector(0.2f, 0.6f, 0.9f); 
	
	private java.util.Vector<Shape> shapes = new java.util.Vector<Shape>();
	
	public Space() {
		
	}
	
	public void add(Shape s) {
		shapes.add(s);
	}
	
	public Vector trace(Ray r, int depth) {

		Intersect intersect = null;
		
		for(int i = 0; i < shapes.size(); i++) {
			Intersect testIntersect = shapes.get(i).getIntersect(r);
			
			if(Float.isFinite(testIntersect.dist)) {
				if(intersect == null || testIntersect.dist < intersect.dist) {
					intersect = testIntersect;
				}
			}
		}
		
		if(intersect != null) {
			return intersect.shape.color;
		}else {
			return background;
		}
		
	}
	
	
	public static void main(String[] args) {
		
		Space s = new Space();
		Camera c = new Camera(100, 100, new Vector(0, 0, 0), (float)Math.PI/2, (float)Math.PI/2, 0, 0);
		
		s.add(new Sphere(new Vector(0, 0, 0), new Vector(0, 0, -3), 1));
		
		BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		
		for(int i = 0; i < c.getWidth(); i++) {
			for(int j = 0; j < c.getHeight(); j++) {
								
				Vector vecColor = s.trace(c.getRay(i, j), 0);
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
