package engine.core;

import engine.events.MouseManager;
import engine.graphics.*;
import engine.importer.MapImporter;
import engine.platform.PlatformResources;
import engine.structure.Entity;
import engine.structure.Scene;
import game.scene.Battlefield;
import testing.MaterialTest;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;

public class Runtime {
    public static Display display;
    public static boolean isRunning = false;

    private static float lastLoopTime = 0, timeCount = 0;

    public static void start(Display display, Scene scene){
        Runtime.display = display;

        PlatformResources.getSystemStats();

        GraphicsRuntime.load(display);

        Globals.load();

        try {
            SceneRuntime.load(MapImporter.importMapFromFile("src/resources/maps/test_map.json"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        isRunning = true;
    }
    private static float getDelta() {
        double time = glfwGetTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = (float)time;
        return delta * 50;
    }

    public static void loop(){
        Globals.deltaTime = getDelta();
        GraphicsRuntime.beginDraw();
        SceneRuntime.update();
        GraphicsRuntime.endDraw(display);
    }

    public static void end(){
        SceneRuntime.end();
    }
}
