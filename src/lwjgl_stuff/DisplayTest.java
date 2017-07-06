package lwjgl_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DisplayTest {
	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Display Test");
			Display.create();
			// create() throws LWJGLException
		} catch (LWJGLException e) {
			System.err.println("Display wasn't initialized correctly.");
			// Throws an exit code of 1
			System.exit(1);
		}
		// While nobody is trying to close the window
		while (!Display.isCloseRequested()) {
			Display.update();
			// FPS is the parameter
			Display.sync(60);
		}
	}
}
