package engine.utils;

public class MathTools {
    public static float clamp(float var, float min, float max){
        if(var < min){
            return min;
        }
        else if(var > max){
            return max;
        }

        return var;
    }
}
