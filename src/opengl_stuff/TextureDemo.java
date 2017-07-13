package opengl_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextureDemo {

	private Texture wood;

	public TextureDemo() {
		try {
			// Sets the width of the display to 640 and the height to 480
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Episode 3 - OpenGL Rendering");
			// Creates and shows the display
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}

		wood = loadTexture("wood");

		try {
			@SuppressWarnings("unused")
			Texture texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/wood.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		while (!Display.isCloseRequested()) {
			// Clear the 2D contents of the window.
			glClear(GL_COLOR_BUFFER_BIT);
			wood.bind();
			glBegin(GL_TRIANGLES);
			glTexCoord2f(1, 0);
			glVertex2i(450, 10);
			glTexCoord2f(0, 0);
			glVertex2i(10, 10);
			glTexCoord2f(0, 1);
			glVertex2i(10, 450);
			glTexCoord2f(0, 1);
			glVertex2i(10, 450);
			glTexCoord2f(1, 1);
			glVertex2i(450, 450);
			glTexCoord2f(1, 0);
			glVertex2i(450, 10);
			glEnd();
			// Update the contents of the display and check for input.
			Display.update();
			// Wait until we reach 60 frames-per-second.
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}

	private Texture loadTexture(String key) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/" + key + ".png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new TextureDemo();
	}
}