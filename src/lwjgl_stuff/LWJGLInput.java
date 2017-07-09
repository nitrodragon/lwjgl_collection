package lwjgl_stuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;

public class LWJGLInput {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;

	private static final List<Box> shapes = new ArrayList<>(16);
	private static boolean somethingIsSelected = false;
	private static long lastColourChange;
	
	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Input");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			// exit code 1 : failure
			System.exit(1);
		}
		
		shapes.add(new Box(15, 15));
		shapes.add(new Box(150, 150));
		shapes.add(new Box(5, 15));
		shapes.add(new Box(10, 150));
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		while (!Display.isCloseRequested()) {
			// Render Data
			glClear(GL_COLOR_BUFFER_BIT);
			
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				Display.destroy();
				System.exit(0);
			}
			
			
			for (final Box box : shapes) {
				if (Mouse.isButtonDown(0) && box.inBounds(Mouse.getX(), 480 - Mouse.getY()) && !somethingIsSelected) {
					somethingIsSelected = true;
					box.selected = true;
				}
				
				if (Mouse.isButtonDown(2) && box.inBounds(Mouse.getX(), 480 - Mouse.getY()) && !somethingIsSelected) {
					if ((System.currentTimeMillis() - lastColourChange) >= 200) {
						box.randomizeColors();
						lastColourChange = System.currentTimeMillis();
					}
				}
				
				if (Mouse.isButtonDown(1)) {
					box.selected = false;
					somethingIsSelected = false;
				}
				
				if (box.selected) {
					box.update(Mouse.getDX(), -Mouse.getDY());
				}
				
				box.draw();
			}
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		System.exit(0);
	}
	
	private static class Box {
		
		public int x, y;
		boolean selected = false;
		private float colorRed, colorBlue, colorGreen;
		
		Box(int x, int y) {
			this.x = x;
			this.y = y;
			
			Random rand = new Random();
			colorRed = rand.nextFloat();
			colorBlue = rand.nextFloat();
			colorGreen = rand.nextFloat();
		}
		
		boolean inBounds(int mousex, int mousey) {
			return (mousex > x && mousex < x + 50 && mousey > y && mousey < y + 50);
		}
		
		void update(int dx, int dy) {
			x += dx;
			y += dy;
		}
		
		void randomizeColors() {
			Random rand = new Random();
			colorRed = rand.nextFloat();
			colorGreen = rand.nextFloat();
			colorBlue = rand.nextFloat();
		}
		
		void draw() {
			glColor3f(colorRed, colorGreen, colorBlue);
			
			glBegin(GL_QUADS);
				glVertex2f(x, y);
				glVertex2f(x + 50, y);
				glVertex2f(x + 50, y + 50);
				glVertex2f(x, y + 50);
			glEnd();
		}
		
	}
	
}