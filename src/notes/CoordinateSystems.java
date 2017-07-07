package notes;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class CoordinateSystems {

    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Coordinate Systems");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }

        // Initialization code OpenGL
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(1, 1, 1, 1, 1, -1);
        // glOrtho(left, right, bottom, top, zNear, zFar)
        // left = left edge
        // right = offset between right and left edges (NOT PIXELS, UNITS)
        // bottom = offset between top and bottom edges (NOT PIXELS, UNITS)
        // up = top edge
        // zNear, zFar = depth for z-axis // Set to 1, -1 for 2D things
        glMatrixMode(GL_MODELVIEW);

        while (!Display.isCloseRequested()) {
            // Render

            glClear(GL_COLOR_BUFFER_BIT);
            glPointSize(10);
            glBegin(GL_POINTS);
            	glColor3d(0.0, 1.0, 0.0); // Color : GREEN
            	glVertex2f(0, 0); // spaces away from center
            	glColor3d(1.0, 0.0, 0.0); // Color : RED
            	glVertex2f(-1, 0); // spaces away from center
            	glVertex2f(1, 0); // spaces away from center
            	glVertex2f(-1, 0); // spaces away from center
            	glVertex2f(0, 1); // spaces away from center
            	glVertex2f(0, -1); // spaces away from center
            	glColor3d(0.0, 1.0, 1.0); // Color : CYAN
            	glVertex2f(1, 1); // spaces away from center
            	glVertex2f(-1, 1); // spaces away from center
            	glVertex2f(-1, -1); // spaces away from center
            	glVertex2f(1, -1); // spaces away from center
            glEnd();

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
        System.exit(0);
    }
}