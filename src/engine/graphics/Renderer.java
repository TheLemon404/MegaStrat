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
    public static Shader lightingShader = new Shader("src/resources/shaders/lighting.glsl");
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

        RenderQuad.draw();

        lightingShader.unbind();
    }
}
