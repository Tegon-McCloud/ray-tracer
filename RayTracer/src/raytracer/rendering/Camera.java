package raytracer.rendering;

import raytracer.math.Matrix;
import raytracer.math.Ray;
import raytracer.math.Vector;

public class Camera {
	
	private Vector pos;
	
	private int pxWidth, pxHeight;
	
	private float tanHalfHFov, tanHalfVFov;
	
	private Matrix rotation;
	
	public Camera(int width, int height, Vector pos, float vFov, float hFov, float vRot, float hRot) {
		this.pos = pos;
		tanHalfHFov = (float)Math.atan(hFov/2);
		tanHalfVFov = (float)Math.atan(vFov/2);
		
		pxWidth = width;
		pxHeight = height;
		
		
		
		rotation = Matrix.getXRotationMatrix(vRot).mul(Matrix.getYRotationMatrix(hRot));
	}
	
	public Ray getRay(int pxX, int pxY) {
		
		Vector ori = new Vector(pos);
		float xFrac = (pxX + 0.5f)/(float)pxWidth;
		float yFrac = (pxY + 0.5f)/(float)pxHeight;
		
		Vector dir = new Vector(2*tanHalfHFov * xFrac - tanHalfHFov, 2*tanHalfVFov * yFrac - tanHalfVFov, -1);
		dir = dir.normalized();
		dir = dir.mul(rotation);
		
		return new Ray(ori, dir);
	}
	
	public int getWidth() {
		return pxWidth;
	}
	
	public int getHeight() {
		return pxHeight;
	}
	
	// test
	public static void main(String[] args) {
		Camera c = new Camera(5, 5, new Vector(0, 0, 0), (float)Math.PI/2, (float)Math.PI/2, (float)Math.PI/3, 0);
		
		for(int i = 0; i < c.getWidth(); i++) {
			for(int j = 0; j < c.getHeight(); j++) {
				System.out.println(c.getRay(i, j).dir);
			}
		}
		
	}
	
}
