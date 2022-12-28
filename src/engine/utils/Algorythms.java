package engine.utils;

import org.joml.Random;
import org.joml.Vector3f;

import java.awt.*;

public class Algorythms {
    public static Vector3f idToColor(int id){
        float r = ((float)id / (float)300000);
        float g = ((float)id / (float)200000);
        float b = ((float)id / (float)100000);
        return new Vector3f((float)r, (float)g, (float)b);
    }

    public static int colorToId(Vector3f color){
        return (int)color.x * 300000;
    }

    public static int generateId(int min, int max){
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
