package engine.events;

import engine.core.Runtime;

import static org.lwjgl.opengl.GL11.*;

public class DisplayEvents {
    public static void onResize(long window, int width, int height) {
        Runtime.display.width = width;
        Runtime.display.height = height;
        glViewport(0, 0, width, height);
    }
}
