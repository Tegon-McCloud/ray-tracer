package raytracer.rendering;

public class SurfaceProperties {
	
	public float ks, kd, ka, shine; // phong constants
	public float transparency, reflectivity;
	
	public SurfaceProperties(float ks, float kd, float ka, float shine, float transparency, float reflectivity) {
		this.ks = ks;
		this.kd = kd;
		this.ka = ka;
		this.shine = shine;
		this.transparency = transparency;
		this.reflectivity = reflectivity;
	}
	
	
	
}
