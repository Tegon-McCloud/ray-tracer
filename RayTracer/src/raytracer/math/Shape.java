package raytracer.math;

import raytracer.rendering.UVTexture;

public abstract class Shape {
	
	public UVTexture texture;
	
	public float ks, kd, ka, shine; // phong constants
	public float transparency, reflectivity;
	
	public Shape(UVTexture texture) {
		this.texture = texture;
	}

	public abstract Intersect getIntersect(Ray r);
	
	public Vector getColor(Vector surfacePos) {
		float[] texCoords = toTexCoords(surfacePos);
		return texture.getColor(texCoords[0], texCoords[1]);
	}
	
	public abstract float[] toTexCoords(Vector surfacePos);
	
}
