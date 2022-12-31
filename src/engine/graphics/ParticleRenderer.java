package engine.graphics;

import engine.core.Runtime;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

public class ParticleRenderer {
    public static void submit(Shader shader, ParticleInstance particle, Matrix4f view) {
        shader.bind();

        for(int i = 0; i < particle.positions.size(); i++) {
            particle.transform.calculateMatrixPosition(particle.positions.get(i));
            shader.uploadUniform(particle.transform.matrix, "u_transforms[" + i + "]");
        }
        shader.uploadUniform(view, "u_view");
        shader.uploadUniform(Runtime.currentScene.camera.projection, "u_projection");
        shader.uploadUniform(particle.material.color, "u_color");
        shader.uploadUniform(particle.mesh.material.shine, "u_shine");
        shader.uploadUniform(particle.mesh.material.strength, "u_strength");
        shader.uploadUniform(4, "tex");
        particle.mesh.material.texture.bind(4);
        shader.uploadUniform((float) particle.id, "u_tileId");

        drawInstance(particle);

        particle.mesh.material.texture.unbind();

        shader.unbind();
    }

    public static void drawInstance(ParticleInstance particleInstance){
        glBindVertexArray(particleInstance.mesh.id);

        glEnableVertexAttribArray(0);
        if(particleInstance.mesh.uvs != null) {
            glEnableVertexAttribArray(1);
        }
        glEnableVertexAttribArray(2);
        if(particleInstance.mesh.colors != null) {
            glEnableVertexAttribArray(3);
        }

        glDrawElementsInstanced(GL_TRIANGLES, particleInstance.mesh.indices.length, GL_UNSIGNED_INT, 0, particleInstance.positions.size());

        if(particleInstance.mesh.colors != null) {
            glDisableVertexAttribArray(3);
        }
        glDisableVertexAttribArray(2);
        if(particleInstance.mesh.uvs != null) {
            glDisableVertexAttribArray(1);
        }
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }
}
