package engine.graphics;

import engine.core.Globals;
import engine.core.Runtime;
import engine.events.MouseManager;
import engine.platform.PlatformResources;
import engine.types.Texture;
import engine.utils.Algorythms;
import engine.utils.MathTools;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {
    public Texture colorTexture, normalTexture, positionTexture, reflectionTexture, entityTexture;
    public int id, width, height, rb_id;

    public FrameBuffer(int width, int height){
        this.width = width;
        this.height = height;

        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);

        colorTexture = new Texture(width, height);
        normalTexture = new Texture(width, height);
        positionTexture = new Texture(width, height);
        reflectionTexture = new Texture(width, height);
        entityTexture = new Texture(width, height);

        colorTexture.load(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorTexture.id, 0);

        normalTexture.load(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, normalTexture.id, 0);

        positionTexture.load(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, positionTexture.id, 0);

        reflectionTexture.load(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT3, GL_TEXTURE_2D, reflectionTexture.id, 0);

        entityTexture.load(GL_RGBA32F, GL_RGBA, GL_FLOAT);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT4, GL_TEXTURE_2D, entityTexture.id, 0);

        glDrawBuffers(new int[]{GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2, GL_COLOR_ATTACHMENT3, GL_COLOR_ATTACHMENT4});

        rb_id = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rb_id);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rb_id);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int sampleId(){
        glBindFramebuffer(GL_READ_FRAMEBUFFER, id);

        int x = (int)getScreenX();
        int y = (int)getScreenY();
        glReadBuffer(GL_COLOR_ATTACHMENT4);
        FloatBuffer pixelData = BufferUtils.createFloatBuffer(4);

        glReadPixels(x, y, 1, 1, GL_RGBA, GL_FLOAT, pixelData);
        System.out.println(height);
        System.out.println(y);

        glReadBuffer(GL_NONE);

        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);

        return (int)pixelData.get(0);
    }

    private float getScreenX() {
        float x = (MouseManager.mousePosition.x / Runtime.display.width) * PlatformResources.monitor.width;
        return x / Globals.resolution;
    }

    private float getScreenY() {
        float y = PlatformResources.monitor.height - ((MouseManager.mousePosition.y / Runtime.display.height) * PlatformResources.monitor.height);
        y -= 30;
        return y / Globals.resolution;
    }

    public Vector3f samplePosition(){
        glBindFramebuffer(GL_READ_FRAMEBUFFER, id);

        int x = (int)getScreenX();
        int y = (int)getScreenY();
        glReadBuffer(GL_COLOR_ATTACHMENT2);
        FloatBuffer pixelData = BufferUtils.createFloatBuffer(4);

        glReadPixels(x, y, 1, 1, GL_RGBA, GL_UNSIGNED_BYTE, pixelData);

        glReadBuffer(GL_NONE);

        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);

        return null;
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void blit(int tx, int ty){
        glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
        glReadBuffer(GL_COLOR_ATTACHMENT0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0,0, tx, ty);
        glBlitFramebuffer(0, 0, width, height, 0, 0, tx, ty, GL_COLOR_BUFFER_BIT, GL_NEAREST);
    }
}
