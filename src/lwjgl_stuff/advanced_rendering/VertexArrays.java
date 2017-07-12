package lwjgl_stuff.advanced_rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glMatrixMode;

public class VertexArrays {
    // Much faster than Immediate Mode
    // Can modify data on client-side
    // Not as fast as Display Lists
    // Deprecated. Not supported in OpenGL 3
    // ONLY reason to use this is if VBOs aren't supported.
    private VertexArrays() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Immediate Mode");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(1, 1, 1, 1, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        final int amountofVertices = 3;
        final int vertexSize = 2;
        final int colorSize = 3;

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(amountofVertices * vertexSize);
        vertexData.put(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f});
        vertexData.flip();

        FloatBuffer colorData = BufferUtils.createFloatBuffer(amountofVertices * colorSize);
        colorData.put(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f});
        colorData.flip();

        while(!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Allows usage of vertex and color arrays
            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_COLOR_ARRAY);

            // Shows OpenGL where to find our arrays
            glVertexPointer(vertexSize, 0, vertexData);
            glColorPointer(colorSize, 0, colorData);

            // Draws the arrays
            glDrawArrays(GL_TRIANGLES, 0, amountofVertices);

            // Closes it and disables it
            glDisableClientState(GL_VERTEX_ARRAY);
            glDisableClientState(GL_COLOR_ARRAY);

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        System.exit(0);
    }
    public static void main(String[] args) {
        new VertexArrays();
    }
}