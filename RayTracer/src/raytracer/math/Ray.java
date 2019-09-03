package raytracer.math;

public class Ray {
	
	static int index = 0;
	
	public Vector ori, dir;
	
	public Ray(Vector ori, Vector dir) {
		this.ori = ori;
		this.dir = dir;
	}
	
	
	public String toGGB() {
		return "ray(" + ori.toPointGGB() + ", " + dir.toGGB() + ")";
	}
	
}
