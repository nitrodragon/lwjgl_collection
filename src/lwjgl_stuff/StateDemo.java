package lwjgl_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class StateDemo {

	private enum State {
		INTRO, MAIN_MENU, GAME
	}
	
	private State state = State.INTRO;
	
    private StateDemo() {
        
    	try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Game States");
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
            
            render();
            checkInput();
            
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
        System.exit(0);
    }
    
    private void render() { 
    	switch(state) {
    	case INTRO:
    		glColor3f(1.0f, 0, 0);
    		glRectf(0, 0, 640, 480);
    		break;
    	case GAME:
    		glColor3f(0f, 1.0f, 0f);
    		glRectf(0f, 0f, 640f, 480f);
    		break;
    	case MAIN_MENU:
    		glColor3f(0f, 0f, 1.0f);
    		glRectf(0f, 0f, 640f, 480f);
    		break;
    	}
    }
    
    private void checkInput() {
    	switch(state) {
    	case INTRO:
    		if (Keyboard.isKeyDown(Keyboard.KEY_S))
    			state = State.MAIN_MENU;
    		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
    			Display.destroy();
    			System.exit(0);
    		}
    		break;
    	case GAME:
    		if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
    			state = State.MAIN_MENU;
    		}
    		break;
    	case MAIN_MENU:
    		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
    			state = State.GAME;
    		}
    		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
    			state = State.INTRO;
    		}
    		break;
    	
    	}
    }

    public static void main(String[] args) {
		new StateDemo();
	}
}