package lwjgl_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class OpenGLVersionChecker {
    public static void main(String[] args) {
        try {
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        System.out.println("OpenGL version is: " + GL11.glGetString(GL11.GL_VERSION));
        Display.destroy();
    }
}
