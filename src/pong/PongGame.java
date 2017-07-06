package pong;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import entities.AbstractMoveableEntity;

import static org.lwjgl.opengl.GL11.*;

public class PongGame {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	private boolean isRunning = true;
	private Ball ball;
	private Paddle paddle;
	private Paddle playerTwo;
	
	public PongGame() {
		setupDisplay();
		setupOpenGL();
		setupEntities();
		setupTimer();
		while (isRunning) {
			render();
			logic(getDelta());
			input();
			p2input();
			Display.update();
			Display.sync(60);
			if (Display.isCloseRequested()) {
				isRunning = false;
			}
		}
		Display.destroy();
	}
	
	private void p2input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			playerTwo.setDY(-.2);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			playerTwo.setDY(.2);
		} else {
			playerTwo.setDY(0);
		}
	}

	private void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			paddle.setDY(-.2);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			paddle.setDY(.2);
		} else {
			paddle.setDY(0);
		}
	}

	private long lastFrame;
	
	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}

	private void logic(int delta) {
		ball.update(delta);
		paddle.update(delta);
		playerTwo.update(delta);
		if (ball.getX() <= paddle.getX() + paddle.getWidth() 
		&& ball.getX() >= paddle.getX() && ball.getY() >= paddle.getY() 
		&& ball.getY() <= paddle.getY() + paddle.getHeight()) {
			ball.setDX(0.3);
			// TODO Random ball.setDY()
		}
		if (ball.getX() <= playerTwo.getX() + playerTwo.getWidth() 
		&& ball.getX() >= playerTwo.getX() - playerTwo.getWidth() && ball.getY() >= playerTwo.getY() 
		&& ball.getY() <= playerTwo.getY() + playerTwo.getHeight()) {
			ball.setDX(-0.3);
			// TODO Random ball.setDY()
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		ball.draw();
		paddle.draw();
		playerTwo.draw();
	}

	private void setupOpenGL() {
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
	}

	private void setupTimer() {
		lastFrame = getTime();
	}

	private void setupEntities() {
		paddle = new Paddle(10, HEIGHT / 2 - 80 / 2, 10, 80);
		ball = new Ball(WIDTH / 2 - 10 / 2, HEIGHT / 2 - 10 / 2, 10, 10);
		playerTwo = new Paddle(WIDTH - 20, HEIGHT / 2 - 80 / 2, 10, 80);
		ball.setDX(-0.1);
	}

	private void setupDisplay() {
		try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("Pong");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
	}
	
	private static class Paddle extends AbstractMoveableEntity {

		public Paddle(double x, double y, double width, double height) {
			super(x, y, width, height);
		}

		@Override
		public void draw() {
			glRectd(x, y, x + width, y + height);
			
		}

	}
	
	private static class Ball extends AbstractMoveableEntity {

		public Ball(double x, double y, double width, double height) {
			super(x, y, width, height);
		}

		@Override
		public void draw() {
			glRectd(x, y, x + width, y + height);
			
		}

	}

	public static void main(String[] args) {
		new PongGame();
	}
	
}
