package opengl_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class ShiftingCameraLaterally {

    private ShiftingCameraLaterally() {
        
    	try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("X Axis Camera Movement");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        
        float x_cam = 0;

        while (!Display.isCloseRequested()) {
            // Render Code here
            glClear(GL_COLOR_BUFFER_BIT);
            // Put a matrix that's a clone of the original into the matrix stock
            glPushMatrix();
            // Pushes screen laterally, depending on x_cam
            glTranslatef(x_cam, 0, 0);
            
            // Move screen with the Mouse speed
            // if the mouse is in the window and space is pressed
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)
            		&& Mouse.getX() > 0 && Mouse.getX() < 639) {
            	x_cam += Mouse.getDX();
            }
            // True coords of the mouse
            float mousex = Mouse.getX() - x_cam;
            float mousey = 480 - Mouse.getY() - 1;
            
            System.out.println(mousex + ", " + mousey);
            
            glBegin(GL_QUADS);
            	glVertex2i(400, 400);
            	glVertex2i(450, 400);
            	glVertex2i(450, 450);
            	glVertex2i(400, 450);
            glEnd();
            
            glBegin(GL_LINES);
            	glVertex2i(400, 400);
            	glVertex2i(400, 400);
            glEnd();
            
            // Disposes of translations made to the matrix
            glPopMatrix();
            
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        System.exit(0);
    }
    
    public static void main(String[] args) {
		new ShiftingCameraLaterally();
	}
}