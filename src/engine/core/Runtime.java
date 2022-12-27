package engine.core;

import engine.graphics.FrameBuffer;
import engine.graphics.RenderQuad;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.structure.Entity;
import engine.structure.Scene;
import testing.TestScene;

import static org.lwjgl.opengl.GL11.*;

public class Runtime {
    public static Display display;
    public static Scene currentScene = new TestScene();
    public static FrameBuffer frameBuffer;
    public static void start(Display display){
        Runtime.display = display;

        currentScene.load();
        for(Entity entity : currentScene.entities.values()){
            entity.start();
            entity.meshInstance.loadMeshes();
        }

        frameBuffer = new FrameBuffer(display.width / 4, display.height / 4);

        RenderQuad.prepare();

        currentScene.camera.calculateProjection();
    }

    public static void loop(){
        currentScene.update();
        currentScene.camera.calculateView();

        frameBuffer.bind();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for(Entity entity : currentScene.entities.values()){
            entity.update();
            entity.meshInstance.sendToRender();
        }

        frameBuffer.unbind();

        frameBuffer.blit(display.width, display.height);

        Renderer.lightingPass(frameBuffer);
    }

    public static void end(){
        currentScene.end();
        for(Entity entity : currentScene.entities.values()){
            entity.end();
        }
    }
}
