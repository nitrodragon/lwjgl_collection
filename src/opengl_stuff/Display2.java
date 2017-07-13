package opengl_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Display2 {

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("A Fresh New Display");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		while (!Display.isCloseRequested()) {
			// render code
			// input handling code
			 
			// refresh display and poll input
			Display.update();
			// Maintain a 60fps frame rate
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}
	
}
