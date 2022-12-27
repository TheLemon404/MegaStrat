package engine.types;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    public int id, width, height;
    public Texture(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void load(int format, int border, int type){
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, border, type, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }
}
