package raytracer.math;

import raytracer.rendering.SurfaceProperties;
import raytracer.rendering.UVTexture;

public abstract class Shape {
	
	public UVTexture texture;
	
	private SurfaceProperties surface;
	
	public Shape(UVTexture texture, SurfaceProperties surface) {
		this.texture = texture;
		this.surface = surface;
	}

	public abstract Intersect getIntersect(Ray r);
	
	public Vector getColor(Vector surfacePos) {
		float[] texCoords = toTexCoords(surfacePos);
		return texture.getColor(texCoords[0], texCoords[1]);
	}
	
	public abstract float[] toTexCoords(Vector surfacePos);
	
	public SurfaceProperties getSurfaceProperties() {
		return surface;
	}
	
}
