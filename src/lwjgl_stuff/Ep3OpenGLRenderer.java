package lwjgl_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Ep3OpenGLRenderer {

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("I don't get it");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			// exit code 1 : failure
			System.exit(1);
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		while (!Display.isCloseRequested()) {
			// Clears a 2D Canvas.
			glClear(GL_COLOR_BUFFER_BIT);
			/* ">>" denotes a possibly modified piece of OpenGL documentation (http://www.opengl.org/sdk/docs/man/)
            >> glBegin and glEnd delimit the vertices that define a primitive or a group of like primitives.
            >> glBegin accepts a single argument that specifies how the vertices are interpreted.
            All upcoming vertex calls will be taken as points of a quadrilateral until glEnd is called. Since
            this primitive requires four vertices, we will have to call glVertex four times. */
			
			// glBegin() takes an integer as a parameter.
			// all of the set commands are constants
			// GL_POINTS, GL_LINES, GL_TRIANGLES, GL_QUADS, GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, and GL_POLYGONS
			// Don't use GL_QUADS or GL_POLYGONS, as they have been deprecated.
			glBegin(GL_QUADS);
			// >> glVertex commands are used within glBegin/glEnd pairs to specify point, line, and polygon vertices.
            // >> glColor sets the current color. (All subsequent calls to glVertex will be assigned this color)
            // >> The number after 'glVertex'/'glColor' indicates the amount of components. (xyzw/rgba)
            // >> The character after the number indicates the type of arguments.
            // >>      (for 'glVertex' = d: Double, f: Float, i: Integer)
            // >>      (for 'glColor'  = d: Double, f: Float, b: Signed Byte, ub: Unsigned Byte)

			// glVertex() takes its parameters as its location.
			// glColor() takes its parameters and makes it a color.
			
			glColor3f(1.0f, 0.0f, 0.0f);                    // Pure Green
            glVertex2i(0, 0);                               // Upper-left
            
            glColor3b((byte)0, (byte)127, (byte)0);         // Pure Red
            glVertex2d(640, 0);                             // Upper-right
            
            glColor3ub((byte)255, (byte)0, (byte)255);      // Purple
            glVertex2f(640.0f, 480.0f);                     // Bottom-right
            
            glColor3d(0, 0, 1);                             // Pure Blue
            glVertex2i(0, 480);                             // Bottom-left
            
			glEnd();
			
			// refresh display and poll input
			Display.update();
			// Maintain a 60fps frame rate
			Display.sync(60);
		}
		Display.destroy();
		// exit code 0 : success!
		System.exit(0);
	}
	
}
