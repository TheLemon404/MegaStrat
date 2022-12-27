package engine.graphics;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderQuad {
    private static int id;
    private static float[] vertices = {
            -1,-1,0,
            1,-1,0,
            -1,1,0,
            1,1,0
    };

    private static int[] indices = {
            0, 1, 2,
            2, 1, 3
    };

    private static float[] uvs = {
            0,0,
            1,0,
            0,1,
            1,1
    };


    public static void prepare(){
        id = glGenVertexArrays();
        glBindVertexArray(id);

        storeIndices(indices);

        storeAttribute(0, vertices, 3);
        storeAttribute(1, uvs, 2);

        glBindVertexArray(0);

        Renderer.lightingShader.compile();
    }

    private static void storeIndices(int[] indices){
        int ibuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibuffer);
        IntBuffer i = createIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, i, GL_STATIC_DRAW);
    }

    private static void storeAttribute(int number, float[] data, int size){
        int buffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, buffer);
        FloatBuffer b = createFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, b, GL_STATIC_DRAW);
        glVertexAttribPointer(number, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static void draw(){
        glBindVertexArray(id);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }
}
