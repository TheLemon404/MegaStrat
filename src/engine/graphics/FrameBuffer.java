package engine.graphics;

import engine.types.Texture;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {
    public Texture colorTexture, normalTexture, positionTexture;
    public int id, width, height, rb_id;

    public FrameBuffer(int width, int height){
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);

        colorTexture = new Texture(width, height);
        normalTexture = new Texture(width, height);
        positionTexture = new Texture(width, height);

        colorTexture.load(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorTexture.id, 0);

        normalTexture.load(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, normalTexture.id, 0);

        positionTexture.load(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, positionTexture.id, 0);

        glDrawBuffers(new int[]{GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2});

        rb_id = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rb_id);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rb_id);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void blit(){
        glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL_COLOR_BUFFER_BIT, GL_LINEAR);
    }
}
