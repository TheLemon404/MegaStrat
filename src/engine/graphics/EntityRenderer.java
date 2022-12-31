package engine.graphics;

import engine.core.Runtime;
import engine.types.Transform;
import engine.utils.Algorythms;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

public class EntityRenderer {
    public static Shader lightingShader = new Shader("src/resources/shaders/lighting.glsl");
    public static void submit(Shader shader, Mesh mesh, Transform transform, int id){
        shader.bind();

        shader.uploadUniform(transform.matrix, "u_transform");
        shader.uploadUniform(Runtime.currentScene.camera.view, "u_view");
        shader.uploadUniform(Runtime.currentScene.camera.projection, "u_projection");
        shader.uploadUniform(mesh.material.color, "u_color");
        shader.uploadUniform(mesh.material.shine, "u_shine");
        shader.uploadUniform((float)id, "u_id");
        shader.uploadUniform(mesh.material.strength, "u_strength");
        shader.uploadUniform(3, "tex");
        mesh.material.texture.bind(3);

        drawIndexed(mesh);

        mesh.material.texture.unbind();

        shader.unbind();
    }

    public static void drawIndexed(Mesh mesh){
        glBindVertexArray(mesh.id);

        glEnableVertexAttribArray(0);
        if(mesh.uvs != null) {
            glEnableVertexAttribArray(1);
        }
        if(mesh.normals != null) {
            glEnableVertexAttribArray(2);
        }
        if(mesh.colors != null) {
            glEnableVertexAttribArray(3);
        }

        if(mesh.indices != null) {
            glDrawElements(GL_TRIANGLES, mesh.indices.length, GL_UNSIGNED_INT, 0);
        }
        else{
            glDrawArrays(GL_TRIANGLES, 0, mesh.vertices.length / 3);
        }

        if(mesh.colors != null) {
            glDisableVertexAttribArray(3);
        }
        if(mesh.normals != null) {
            glDisableVertexAttribArray(2);
        }
        if(mesh.uvs != null) {
            glDisableVertexAttribArray(1);
        }
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }

    public static void lightingPass(FrameBuffer frameBuffer){
        lightingShader.bind();

        lightingShader.uploadUniform(Runtime.currentScene.sun.color, "u_lightColor");

        lightingShader.uploadUniform(0, "u_position");
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, frameBuffer.positionTexture.id);

        lightingShader.uploadUniform(1, "u_normal");
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, frameBuffer.normalTexture.id);

        lightingShader.uploadUniform(2, "u_color");
        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, frameBuffer.colorTexture.id);

        lightingShader.uploadUniform(3, "u_camera");
        glActiveTexture(GL_TEXTURE3);
        glBindTexture(GL_TEXTURE_2D, frameBuffer.reflectionTexture.id);

        lightingShader.uploadUniform(new Vector3f(-Runtime.currentScene.lightDirection.x, -Runtime.currentScene.lightDirection.y, -Runtime.currentScene.lightDirection.z), "u_lightDirection");

        RenderQuad.draw();

        lightingShader.unbind();
    }
}
