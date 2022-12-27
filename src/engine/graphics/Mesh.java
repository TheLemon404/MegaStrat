package engine.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Mesh {
    public int id;
    public Vector3f subRotation = new Vector3f();
    public Material material = new Material();
    public float[] vertices = {
            -0.5f,-0.5f,0,
            0.5f,-0.5f,0,
            -0.5f,0.5f,0,
            0.5f,0.5f,0
    };

    public int[] indices = {
            0, 1, 2,
            2, 1, 3
    };

    public float[] uvs = {

    };

    public float[] normals = {

    };

    public void load(){
        id = glGenVertexArrays();
        glBindVertexArray(id);

        storeIndices(indices);

        storeAttribute(0, vertices, 3);
        storeAttribute(1, uvs, 2);
        storeAttribute(2, normals, 3);

        glBindVertexArray(0);
    }

    private void storeIndices(int[] indices){
        int ibuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibuffer);
        IntBuffer i = createIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, i, GL_STATIC_DRAW);
    }

    private void storeAttribute(int number, float[] data, int size){
        int buffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, buffer);
        FloatBuffer b = createFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, b, GL_STATIC_DRAW);
        glVertexAttribPointer(number, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public IntBuffer createIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
