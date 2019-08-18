package raytracer;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		
		if(args.length > 0) {
			renderFromFile(new File(args[0]));
		}else {
			startCommandEngine();
		}
		
	}
	
	public static void renderFromFile(File f) {
		
	}
	
	public static void startCommandEngine() {
		
	}
	
}
