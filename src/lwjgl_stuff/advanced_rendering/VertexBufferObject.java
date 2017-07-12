package lwjgl_stuff.advanced_rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObject {
    // *Much* faster than Immediate Mode
    // Can modify data on client-side
    // About as fast as Display Lists
    // *Not deprecated—still in use.*
    // Most complicated of all.
    // Of course.
    private VertexBufferObject() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("VBO Rendering");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(1, 1, 1, 1, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        final int amountOfVertices = 3;
        final int vertexSize = 2;
        final int colorSize = 3;

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(amountOfVertices * vertexSize);
        vertexData.put(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f});
        vertexData.flip();

        FloatBuffer colorData = BufferUtils.createFloatBuffer(amountOfVertices * colorSize);
        colorData.put(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f});
        colorData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboColorHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
        glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);

            glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
            glVertexPointer(vertexSize, GL_FLOAT, 0, 0L);

            glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
            glVertexPointer(colorSize, GL_FLOAT, 0, 0L);

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_COLOR_ARRAY);
            glDrawArrays(GL_TRIANGLES, 0, 1);
            glDisableClientState(GL_VERTEX_ARRAY);
            glDisableClientState(GL_COLOR_ARRAY);

            Display.update();
            Display.sync(60);

        }
        glDeleteBuffers(vboVertexHandle);
        glDeleteBuffers(vboColorHandle);

        Display.destroy();
        System.exit(0);
    }

    public static void main(String[] args) {
        new VertexBufferObject();
    }
}

