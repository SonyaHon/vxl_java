package me.sonyahon.engine.display;

import me.sonyahon.Reference;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {

    public static long MAIN_WINDOW = NULL;

    public static void createDisplay() {
        try {
            GLFWErrorCallback.createPrint(System.err).set();
            if (!glfwInit())
                throw new IllegalStateException("Unable to initialize GLFW");


            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);


            MAIN_WINDOW = glfwCreateWindow(Reference.DISPLAY_WIDTH, Reference.DISPLAY_HEIGHT, Reference.WINDOW_TITLE, NULL, NULL);
            if (MAIN_WINDOW == NULL)
                throw new RuntimeException("Failed to create the GLFW window");

            glfwSetKeyCallback(MAIN_WINDOW, (window, key, scancode, action, mods) -> {
                // If we will need some event base interactions (like hit escape - go to the pause menu)
                // this should be implemented here.
            });
            glfwSetInputMode(MAIN_WINDOW, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

            int realW = 0;
            int realH = 0;
            try (MemoryStack stack = stackPush()) {
                IntBuffer pWidth  = stack.mallocInt(1); // int*
                IntBuffer pHeight = stack.mallocInt(1); // int*

                glfwGetWindowSize(MAIN_WINDOW, pWidth, pHeight);

                realW = pWidth.get(0);
                realH = pHeight.get(0);

                GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

                assert vidMode != null;
                glfwSetWindowPos(
                        MAIN_WINDOW,
                        (vidMode.width() - realW) / 2,
                        (vidMode.height() - realH) / 2
                );
            } // the stack frame is popped automatically


            glfwMakeContextCurrent(MAIN_WINDOW);
            glfwSwapInterval(Reference.VSYNC ? 1 : 0);
            glfwShowWindow(MAIN_WINDOW);


            GL.createCapabilities();
            setDefaultViewport();
            GL11.glEnable(GL_DEPTH_TEST);

        } catch (RuntimeException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void setDefaultViewport() {
        // @TODO detect retina displays and fix this bit
        GL11.glViewport(0, 0, Reference.DISPLAY_WIDTH, Reference.DISPLAY_HEIGHT);
    }

    public static void clearDisplay() {
        glClearColor(Reference.CLEAR_COLOR.x, Reference.CLEAR_COLOR.y, Reference.CLEAR_COLOR.z, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void updateDisplay() {
        glfwSwapBuffers(MAIN_WINDOW);
        glfwPollEvents();
    }

    public static void drop() {
        glfwDestroyWindow(MAIN_WINDOW);
        glfwFreeCallbacks(MAIN_WINDOW);
        glfwDestroyWindow(MAIN_WINDOW);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public static boolean shouldMainWindowClose() {
        return glfwWindowShouldClose(MAIN_WINDOW);
    }
}
