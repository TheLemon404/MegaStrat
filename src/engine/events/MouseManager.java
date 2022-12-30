package engine.events;

import engine.core.Globals;
import engine.core.Runtime;
import engine.platform.PlatformResources;
import org.joml.Vector2f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseManager {
    private static ArrayList<Integer> upButtons = new ArrayList<>();
    private static ArrayList<Integer> downButtons = new ArrayList<>();
    public static Vector2f mousePosition = new Vector2f();
    public static Vector2f mouseDelta = new Vector2f();
    public static float scrollDelta;
    public static boolean isButtonDown(int key){
        if(downButtons.contains(key)){
            return true;
        }

        return false;
    }

    public static float getScreenX() {
        float x = (MouseManager.mousePosition.x / Runtime.display.width) * PlatformResources.monitor.width;
        return x / Globals.resolution;
    }

    public static float getScreenY() {
        float y = PlatformResources.monitor.height - ((MouseManager.mousePosition.y / Runtime.display.height) * PlatformResources.monitor.height);
        return y / Globals.resolution;
    }

    public static boolean isKeyUp(int key){
        if(upButtons.contains(key)){
            return true;
        }

        return true;
    }
    private static void removeButton(ArrayList<Integer> array, int button){
        for(int i = 0; i < array.size(); i++){
            if(array.get(i) == button){
                array.remove(i);
            }
        }
    }
    public static void buttonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if(!downButtons.contains(button) && !upButtons.contains(button)){
                downButtons.add(button);
            }
            else if(upButtons.contains(button)){
                removeButton(upButtons, button);
                downButtons.add(button);
            }
        }
        else if(action == GLFW_RELEASE){
            if(downButtons.contains(button)){
                removeButton(downButtons, button);
                upButtons.add(button);
            }
        }
    }

    public static void cursorCallback(long window, double x, double y) {
        mouseDelta.x = mousePosition.x - (float)x;
        mouseDelta.y = mousePosition.y - (float)y;
        mousePosition.x = (float)x;
        mousePosition.y = (float)y;
        scrollDelta = 0;
    }

    public static void scrollCallback(long window, double x, double y) {
        scrollDelta = (float)y;
    }
}