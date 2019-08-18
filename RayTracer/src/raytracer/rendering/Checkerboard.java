package raytracer.rendering;

import raytracer.math.Vector;

public class Checkerboard extends UVTexture{

	public Vector color1, color2;
	public int squaresPerSide;
	
	public Checkerboard(Vector color1, Vector color2, int squaresPerSide) {
		this.color1 = color1;
		this.color2 = color2;
		this.squaresPerSide = squaresPerSide;
	}

	@Override
	public Vector getColor(float texX, float texY) {
		boolean isColor1 = true;
		if((int) Math.floor(texX * squaresPerSide) % 2 == 0) {
			isColor1 = !isColor1;
		}
		if((int) Math.floor(texY * squaresPerSide) % 2 == 0) {
			isColor1 = !isColor1;
		}
		return isColor1 ? color1 : color2; 
	}

}
