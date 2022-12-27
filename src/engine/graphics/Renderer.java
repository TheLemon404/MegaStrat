package engine.graphics;

import engine.core.Runtime;
import engine.types.Transform;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
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

    private static Shader lightingShader = new Shader("src/resources/shaders/lighting.glsl");

    public static void prepare(){
        id = glGenVertexArrays();
        glBindVertexArray(id);

        storeIndices(indices);

        storeAttribute(0, vertices, 3);
        storeAttribute(1, uvs, 2);

        glBindVertexArray(0);

        lightingShader.compile();
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
    public static void submit(Shader shader, Mesh mesh, Transform transform){
        shader.bind();

        shader.uploadUniform(transform.matrix, "u_transform");
        shader.uploadUniform(Runtime.currentScene.camera.view, "u_view");
        shader.uploadUniform(Runtime.currentScene.camera.projection, "u_projection");

        drawIndexed(mesh);

        shader.unbind();
    }

    public static void drawIndexed(Mesh mesh){
        glBindVertexArray(mesh.id);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, mesh.indices.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }

    public static void lightingPass(FrameBuffer frameBuffer){
        lightingShader.bind();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        lightingShader.uploadUniform(0, "u_position");
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, frameBuffer.positionTexture.id);

        lightingShader.uploadUniform(1, "u_normal");
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, frameBuffer.normalTexture.id);

        lightingShader.uploadUniform(2, "u_color");
        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, frameBuffer.colorTexture.id);
        lightingShader.uploadUniform(new Vector3f(-1, -1, 0), "u_lightDirection");

        renderQuad();

        lightingShader.unbind();
    }

    public static void renderQuad(){
        glBindVertexArray(id);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }
}
