package raytracer.rendering;

public class SurfaceProperties {
	
	public float ks, kd, ka, shine; // phong constants
	public boolean reflective, refractive;
	public float ior;
	
	public SurfaceProperties(float ks, float kd, float ka, float shine, boolean reflective, boolean refractive) {
		this.ks = ks;
		this.kd = kd;
		this.ka = ka;
		this.shine = shine;
		this.reflective = reflective;
		this.refractive = refractive;
	}
	
	
	
}
