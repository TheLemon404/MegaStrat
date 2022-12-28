package engine.events;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardManager {
    private static ArrayList<Integer> downKeys = new ArrayList<>();
    private static ArrayList<Integer> upKeys = new ArrayList<>();
    private static ArrayList<Integer> justDown = new ArrayList<>();
    public static boolean isKeyDown(int key){
        if(downKeys.contains(key)){
            return true;
        }

        return false;
    }
    public static boolean isKeyJustDown(int key){
        if(justDown.contains(key)){
            return true;
        }

        return false;
    }

    public static boolean isKeyUp(int key){
        if(upKeys.contains(key)){
            return true;
        }

        return true;
    }
    private static void removeKey(ArrayList<Integer> array, int key){
        for(int i = 0; i < array.stream().count(); i++){
            if(array.get(i) == key){
                array.remove(i);
            }
        }
    }

    public static void callback(long window, int key, int scanCode, int action, int mods) {
        if(action == GLFW_PRESS){
            if(upKeys.contains(key)){
                removeKey(upKeys, key);
                downKeys.add(key);
                if(!justDown.contains(key)){
                    justDown.add(key);
                }
                else{
                    removeKey(justDown, key);
                }
            }
            if(!upKeys.contains(key) && !downKeys.contains(key)){
                downKeys.add(key);
                if(!justDown.contains(key)){
                    justDown.add(key);
                }
                else{
                    removeKey(justDown, key);
                }
            }
        }
        else if(action == GLFW_RELEASE){
            if(downKeys.contains(key)){
                removeKey(downKeys, key);
                upKeys.add(key);
            }
        }
    }
}
