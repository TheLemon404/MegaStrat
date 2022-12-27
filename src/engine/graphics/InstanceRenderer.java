package engine.graphics;

import engine.core.Runtime;
import engine.types.Transform;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

public class InstanceRenderer {
    public static void submit(Shader shader, Instance instance) {
        shader.bind();

        for (int i = 0; i < instance.transforms.size(); i++){
            shader.uploadUniform(instance.transforms.get(i).matrix, "u_transforms[" + i + "]");
        }
        shader.uploadUniform(Runtime.currentScene.camera.view, "u_view");
        shader.uploadUniform(Runtime.currentScene.camera.projection, "u_projection");
        shader.uploadUniform(instance.mesh.material.color, "u_color");

        drawInstance(instance);

        instance.mesh.material.texture.unbind();

        shader.unbind();
    }

    public static void drawInstance(Instance instance){
        glBindVertexArray(instance.mesh.id);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        if(instance.mesh.colors != null) {
            glEnableVertexAttribArray(3);
        }

        glDrawElementsInstanced(GL_TRIANGLES, instance.mesh.indices.length, GL_UNSIGNED_INT, 0, instance.transforms.size());

        if(instance.mesh.colors != null) {
            glDisableVertexAttribArray(3);
        }
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }
}
