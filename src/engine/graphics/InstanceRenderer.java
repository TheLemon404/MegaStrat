package engine.graphics;

import engine.core.Runtime;
import engine.types.Transform;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

public class InstanceRenderer {
    public static void submit(Shader shader, Instance instance, Matrix4f view) {
        shader.bind();

        for (int i = 0; i < instance.transforms.size(); i++){
            shader.uploadUniform(instance.transforms.get(i).matrix, "u_transforms[" + i + "]");
        }
        shader.uploadUniform(view, "u_view");
        shader.uploadUniform(Runtime.currentScene.camera.projection, "u_projection");
        for (int i = 0; i < instance.materials.size(); i++){
            shader.uploadUniform(instance.materials.get(i).color, "u_color[" + i + "]");
        }
        shader.uploadUniform(instance.mesh.material.shine, "u_shine");
        shader.uploadUniform(instance.mesh.material.strength, "u_strength");
        shader.uploadUniform(4, "tex");
        instance.mesh.material.texture.bind(4);
        for (int i = 0; i < instance.ids.size(); i++) {
            shader.uploadUniform((float) instance.ids.get(i), "u_tileId[" + i + "]");
        }

        drawInstance(instance);

        instance.mesh.material.texture.unbind();

        shader.unbind();
    }

    public static void drawInstance(Instance instance){
        glBindVertexArray(instance.mesh.id);

        glEnableVertexAttribArray(0);
        if(instance.mesh.uvs != null) {
            glEnableVertexAttribArray(1);
        }
        glEnableVertexAttribArray(2);
        if(instance.mesh.colors != null) {
            glEnableVertexAttribArray(3);
        }

        glDrawElementsInstanced(GL_TRIANGLES, instance.mesh.indices.length, GL_UNSIGNED_INT, 0, instance.transforms.size());

        if(instance.mesh.colors != null) {
            glDisableVertexAttribArray(3);
        }
        glDisableVertexAttribArray(2);
        if(instance.mesh.uvs != null) {
            glDisableVertexAttribArray(1);
        }
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }
}
