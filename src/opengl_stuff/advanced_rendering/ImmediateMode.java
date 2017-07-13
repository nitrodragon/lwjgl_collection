package opengl_stuff.advanced_rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class ImmediateMode {

    // Easy and understandable, but incredibly slow
    // and outdated—no longer supported in OpenGL
    private ImmediateMode() {

        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Immediate Mode");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        while (!Display.isCloseRequested()) {
            // Render Code here
            glClear(GL_COLOR_BUFFER_BIT);

            glBegin(GL_QUADS); // Denotes Immediate Mode
            glVertex2i(400, 400);
            glVertex2i(450, 400);
            glVertex2i(450, 450);
            glVertex2i(400, 450);
            glEnd(); // Denotes Immediate Mode

            glBegin(GL_LINES); // Denotes Immediate Mode
            glVertex2i(400, 400);
            glVertex2i(400, 800);
            glEnd(); // Denotes Immediate Mode

            glBegin(GL_TRIANGLES);
            glColor3f(100, 0,0);
            glVertex2f(-50.5f, -50.5f);
            glColor3f(0, 1,0);
            glVertex2f(50.5f, -50.5f);
            glColor3f(0, 0,1);
            glVertex2f(50.5f, 50.5f);
            glEnd();

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        System.exit(0);
    }

    public static void main(String[] args) {
        new ImmediateMode();
    }
}