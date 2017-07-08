package notes;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class IntroTo3D {

    public static void main(String[] args) {
        // Initialization code Display
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Heckin Star Wars");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
        // Initialization code OpenGL
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        // gluPerspective(field-of-view (angle in degrees), aspect ratio (width / height), zNear, zFar)
        // zNear = minimum amount of distance for objects to be drawn
        // zFar = maximum amount of distance for objects to be drawn
        gluPerspective((float) 30, 640f / 480f, 0.001f, 100);
        glMatrixMode(GL_MODELVIEW);

        // To make sure the points closest to the camera are shown in front of the points that are farther away.
        glEnable(GL_DEPTH_TEST);

        // Initialization code for random points
        Point[] points = new Point[10000];
        Random random = new Random();
        // Iterate of every array index
        for (int i = 0; i < points.length; i++) {
            // Set the point at the array index to 
            // x = random between -50 and +50
            // y = random between -50 and +50
            // z = random between  0  and -200
            points[i] = new Point((random.nextFloat() - 0.5f) * 100f, (random.nextFloat() - 0.5f) * 100f, random.nextInt(200) - 200);
        }
        // The speed in which the "camera" travels
        float speed = 0.0f;
        //

        while (!Display.isCloseRequested()) {
            // Render

            // Clear the screen of its filthy contents
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Push the screen inwards at the amount of speed
            glTranslatef(0, 0, speed);

            // Begin drawing points
            glBegin(GL_POINTS);
            glPointSize(5);
            // Iterate of every point
            for (Point p : points) {
                // Draw the point at its coordinates
                glVertex3f(p.x, p.y, p.z);
            }
            // Stop drawing points
            glEnd();

            // If we're pressing the "up" key increase our speed
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                speed += 0.01f;
            }
            // If we're pressing the "down" key decrease our speed
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                speed -= 0.01f;
            }
            // Iterate over keyboard input events
            while (Keyboard.next()) {
                // If we're pressing the "space-bar" key reset our speed to zero
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    speed = 0f;
                }
                // If we're pressing the "c" key reset our speed to zero and reset our position
                if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
                    speed = 0;
                    glLoadIdentity();
                }
            }

            // Update the display
            Display.update();
            // Wait until the frame-rate is 60fps cap it there
            Display.sync(60);
        }

        // Dispose of the display
        Display.destroy();
        // Exit the JVM (for some reason this lingers on Mac, also good practice)
        System.exit(0);
    } 

    private static class Point {

        final float x;
        final float y;
        final float z;

        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
