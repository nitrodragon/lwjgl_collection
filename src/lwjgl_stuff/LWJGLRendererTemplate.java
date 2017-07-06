package lwjgl_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class LWJGLRendererTemplate {

    public LWJGLRendererTemplate() {
        
    	try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("LWJGL Template");
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
            
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        System.exit(0);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
		new LWJGLRendererTemplate();
	}
}
