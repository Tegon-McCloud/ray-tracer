package raytracer.rendering;

import raytracer.math.Vector;

public abstract class UVTexture {
	public abstract Vector getColor(float texX, float texY);
}
