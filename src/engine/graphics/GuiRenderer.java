package engine.graphics;

import engine.core.Runtime;
import engine.core.SceneRuntime;
import engine.gui.Rect;
import engine.gui.Widget;
import engine.gui.Window;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

public class GuiRenderer {
    public static void submit(Shader shader, Widget widget, Rect rect) {
        shader.bind();

        shader.uploadUniform(SceneRuntime.currentScene.camera.ui, "u_projection");
        shader.uploadUniform(rect.matrix, "u_rect");
        shader.uploadUniform(widget.color, "u_color");

        shader.uploadUniform(4, "tex");
        widget.mesh.material.texture.bind(4);

        drawGui(widget);

        widget.mesh.material.texture.unbind();

        shader.unbind();
    }

    public static void submit(Shader shader, Window window, Rect rect) {
        shader.bind();

        shader.uploadUniform(SceneRuntime.currentScene.camera.ui, "u_projection");
        shader.uploadUniform(rect.matrix, "u_rect");
        shader.uploadUniform(window.color, "u_color");

        shader.uploadUniform(4, "tex");
        window.mesh.material.texture.bind(4);

        drawGui(window);

        window.mesh.material.texture.unbind();

        shader.unbind();
    }

    public static void drawGui(Widget widget){
        glBindVertexArray(widget.mesh.id);

        glEnableVertexAttribArray(0);
        if(widget.mesh.uvs != null) {
            glEnableVertexAttribArray(1);
        }
        glEnableVertexAttribArray(2);
        if(widget.mesh.colors != null) {
            glEnableVertexAttribArray(3);
        }

        glDrawElements(GL_TRIANGLES, widget.mesh.indices.length, GL_UNSIGNED_INT, 0);

        if(widget.mesh.colors != null) {
            glDisableVertexAttribArray(3);
        }
        glDisableVertexAttribArray(2);
        if(widget.mesh.uvs != null) {
            glDisableVertexAttribArray(1);
        }
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }

    public static void drawGui(Window window){
        glBindVertexArray(window.mesh.id);

        glEnableVertexAttribArray(0);
        if(window.mesh.uvs != null) {
            glEnableVertexAttribArray(1);
        }
        glEnableVertexAttribArray(2);
        if(window.mesh.colors != null) {
            glEnableVertexAttribArray(3);
        }

        glDrawElements(GL_TRIANGLES, window.mesh.indices.length, GL_UNSIGNED_INT, 0);

        if(window.mesh.colors != null) {
            glDisableVertexAttribArray(3);
        }
        glDisableVertexAttribArray(2);
        if(window.mesh.uvs != null) {
            glDisableVertexAttribArray(1);
        }
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }
}
