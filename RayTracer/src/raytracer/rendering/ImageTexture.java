package raytracer.rendering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import raytracer.math.Vector;

public class ImageTexture extends UVTexture {
	
	public BufferedImage bi;

	public ImageTexture(File imgFile) throws IOException {
		bi = ImageIO.read(imgFile);
	}

	@Override
	public Vector getColor(float texX, float texY) {
		
		int rgb = bi.getRGB((int)(texX * bi.getWidth()), (int)(texY * bi.getHeight()));
		Color color = new Color(rgb);
		return new Vector(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f);
		
	}
	
}
