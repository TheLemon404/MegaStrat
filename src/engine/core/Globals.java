package engine.core;

import engine.graphics.Shader;

public class Globals {
    public static Shader entityShader = new Shader("src/resources/shaders/entity.glsl");

    public static Shader instanceShader = new Shader("src/resources/shaders/instance.glsl");

    public static int resolution = 1;

    public static void load(){
        entityShader.compile();
        instanceShader.compile();
    }
}
