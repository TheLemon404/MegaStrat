package engine.core;

import engine.events.DisplayEvents;
import engine.events.KeyboardManager;
import engine.events.MouseManager;
import engine.structure.Scene;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Display {
    private String title;
    private long window;
    public int width, height;
    public Display(String title, int width, int height, Scene scene){
        this.width = width;
        this.height = height;
        this.title = title;

        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwSetWindowSizeCallback(window, DisplayEvents::onResize);
        glfwSetKeyCallback(window, KeyboardManager::callback);
        glfwSetMouseButtonCallback(window, MouseManager::buttonCallback);
        glfwSetCursorPosCallback(window, MouseManager::cursorCallback);
        glfwSetScrollCallback(window, MouseManager::scrollCallback);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        createCapabilities();

        glfwShowWindow(window);

        Runtime.start(this, scene);

        while(!glfwWindowShouldClose(window)){
            glEnable(GL_CULL_FACE);
            glClearColor(0.8f, 0.9f, 1,1);

            Runtime.loop();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        Runtime.end();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
