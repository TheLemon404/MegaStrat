package engine.utils;

import org.joml.Random;
import org.joml.Vector3f;

import java.awt.*;

public class Algorythms {
    public static int generateId(int min, int max){
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
