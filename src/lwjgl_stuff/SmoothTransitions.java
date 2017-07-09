package lwjgl_stuff;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class SmoothTransitions {

	private enum State {
        // Draws nothing. Waits for input then switches to FADING.
        INTRO,
        // Slowly fades in a blue rectangle. When it opacity is 100,
        // the state is set to main.
        FADING,
        // Rectangle is fully shown. If enter is pressed, it sends
        // you back to INTRO.
        MAIN
    }
	
	private SmoothTransitions() {
        // Of course, the default State is INTRO.
        State state = State.INTRO;
		try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("LWJGL Template");
            Display.setVSyncEnabled(true); // prevents tearing and choppy animation??
            Display.create();
        } catch (LWJGLException e) {
            System.err.println("Display failed to initialize.");
            System.exit(1);
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(1, 1, 1, 1, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		// Fade in degrees (0 to 90)
		float fade = 0f;
		
		while (!Display.isCloseRequested()) {
			// Clear
			glClear(GL_COLOR_BUFFER_BIT);
			
			switch(state) {
				case FADING:
					if (fade < 90) {
						fade += 1.5f;
					} else {
						fade = 0;
						glColor3f(0.5f, 0.5f, 1);
						glRectf(-0.5f, -0.5f, 0.5f, 0.5f);
						state = State.MAIN;
						System.out.println("State changed: " + state);
						break;
					}
					// Opacity = sin(fade)
					glColor4d(0.5, 0.5, 1f, Math.sin(Math.toRadians(fade)));
					// Draws rectangle
					glRectf(-0.5f, -0.5f, 0.5f, 0.5f);
					break;
				case INTRO:
					break;
				case MAIN:
					// Draw the fully opaque rectangle
					glColor3f(0.5f, 0.5f, 1);
					glRectf(-0.5f, -0.5f, 0.5f, 0.5f);
					break;
			}
			
			while (Keyboard.next()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
					switch (state) {
						case FADING:
							fade = 0;
							state = State.MAIN;
							System.out.println("State changed: " + state);
							break;
						case INTRO:
							state = State.FADING;
							System.out.println("State changed: " + state);
							break;
						case MAIN:
							state = State.INTRO;
							System.out.println("State changed: " + state);
							break;
					}
				}
			}
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new SmoothTransitions();
	}
}
