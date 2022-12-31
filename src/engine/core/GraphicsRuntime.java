package engine.core;

import engine.graphics.EntityRenderer;
import engine.graphics.FrameBuffer;
import engine.graphics.RenderQuad;
import engine.gui.GuiLayer;

import static org.lwjgl.opengl.GL11.*;

public class GraphicsRuntime {
    public static FrameBuffer frameBuffer;

    public static void load(Display display){
        frameBuffer = new FrameBuffer(display.width / Globals.resolution, display.height / Globals.resolution);

        RenderQuad.prepare();
    }

    public static void beginDraw(){
        frameBuffer.bind();

        glEnable(GL_DEPTH_TEST);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    public static void endDraw(Display display){
        frameBuffer.unbind();

        frameBuffer.blit(display.width, display.height);

        glDisable(GL_DEPTH_TEST);

        EntityRenderer.lightingPass(frameBuffer);

        GuiLayer.draw();
    }
}
