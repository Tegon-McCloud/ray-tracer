package raytracer.math;

public class DistantLight {
	
	public Vector id, is;
	public Vector dir;
	
	public DistantLight(Vector dir, Vector diffuseColor, Vector specularColor) {
		this.dir = dir;
		id = diffuseColor;
		is = specularColor;
	}
	
}
