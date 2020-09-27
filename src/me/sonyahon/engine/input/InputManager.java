package me.sonyahon.engine.input;

import me.sonyahon.engine.display.DisplayManager;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class InputManager {
    public static InputManager instance = new InputManager();

    public boolean isKeyPressed(int key) {
        int res = glfwGetKey(DisplayManager.MAIN_WINDOW, key);
        return res == GLFW_PRESS;
    }

    public Vector2f getMouseCoordinates() {
        double[] x = new double[1];
        double[] y = new double[1];
        GLFW.glfwGetCursorPos(DisplayManager.MAIN_WINDOW, x, y);
        return new Vector2f((float) x[0], (float) y[0]);
    }
}
