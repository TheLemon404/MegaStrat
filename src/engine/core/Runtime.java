package engine.core;

import engine.events.MouseManager;
import engine.graphics.*;
import engine.gui.GuiLayer;
import engine.platform.PlatformResources;
import engine.structure.Entity;
import engine.structure.Scene;
import game.scene.Battlefield;
import testing.MaterialTest;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.opengl.GL11.*;

public class Runtime {
    public static Display display;
    public static boolean isRunning = false;

    private static float beginTime = 0, endTime = 0, startTime = 0;

    public static void start(Display display, Scene scene){
        Runtime.display = display;

        PlatformResources.getSystemStats();

        startTime = (float)System.nanoTime();

        GraphicsRuntime.load(display);
        SceneRuntime.load(new Battlefield());

        Globals.load();

        GuiLayer.load();

        isRunning = true;
    }

    public static void loop(){
        GraphicsRuntime.beginDraw();
        SceneRuntime.update();
        GraphicsRuntime.endDraw(display);
    }

    public static void end(){
        SceneRuntime.end();
    }
}
