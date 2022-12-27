package engine.core;

import engine.graphics.FrameBuffer;
import engine.graphics.RenderQuad;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.structure.Entity;
import engine.structure.Scene;
import testing.TestScene;

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

        frameBuffer = new FrameBuffer(display.width, display.height);

        RenderQuad.prepare();

        currentScene.camera.calculateProjection();
    }

    public static void loop(){
        currentScene.update();
        currentScene.camera.calculateView();

        frameBuffer.bind();

        for(Entity entity : currentScene.entities.values()){
            entity.update();
            entity.meshInstance.sendToRender();
        }

        frameBuffer.unbind();

        Renderer.lightingPass(frameBuffer);
    }

    public static void end(){
        currentScene.end();
        for(Entity entity : currentScene.entities.values()){
            entity.end();
        }
    }
}
