package pong;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import entities.AbstractMoveableEntity;  

import static org.lwjgl.opengl.GL11.*;

import java.util.concurrent.ThreadLocalRandom;

public class PongGame {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	private boolean isRunning = true;
	private Ball ball;
	private Paddle paddle;
	private Paddle playerTwo;
	private int p1Score;
	private int p2Score;
	
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
	
	private void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			paddle.setDY(-.5);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			paddle.setDY(.5);
		} else {
			paddle.setDY(0);
		}
	}

	private void p2input() throws IllegalStateException {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			playerTwo.setDY(-.5);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			playerTwo.setDY(.5);
		} else {
			playerTwo.setDY(0);
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
			ball.setDY(ThreadLocalRandom.current().nextDouble(-0.3, 0.4));
		}
		if (ball.getX() <= playerTwo.getX() + playerTwo.getWidth() 
		&& ball.getX() >= playerTwo.getX() - playerTwo.getWidth() && ball.getY() >= playerTwo.getY() 
		&& ball.getY() <= playerTwo.getY() + playerTwo.getHeight()) {
			ball.setDX(-0.3);
			ball.setDY(ThreadLocalRandom.current().nextDouble(-0.3, 0.4));
		}
		if (ball.getX() <= 0) {
			p2Score++;
			ball.setX(WIDTH / 2 - 10 / 2);
			ball.setY(HEIGHT / 2 - 10 / 2);
			ball.setDX(0.1);
			ball.setDY(0.0);
		}
		if (ball.getX() >= (WIDTH - ball.getWidth())) {
			p1Score++;
			ball.setX(WIDTH / 2 - 10 / 2);
			ball.setY(HEIGHT / 2 - 10 / 2);
			ball.setDX(-0.1);
			ball.setDY(0.0);
		}
		if (ball.getY() <= 0) {
			ball.setDY(0.3);

		}
		if (ball.getY() >= (HEIGHT - ball.getHeight())) {
			ball.setDY(-0.3);
		}
		if (p1Score >= 5) {
			System.out.println("P1 Wins with a score of " + p1Score + "!");
			Display.destroy();
			System.exit(0);
		}
		if (p2Score >= 5) {
			System.out.println("P2 Wins with a score of " + p2Score + "!");
			Display.destroy();
			System.exit(0);
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
