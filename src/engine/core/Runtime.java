package engine.core;

import engine.events.MouseManager;
import engine.graphics.FrameBuffer;
import engine.graphics.Instance;
import engine.graphics.RenderQuad;
import engine.graphics.EntityRenderer;
import engine.gui.GuiLayer;
import engine.platform.PlatformResources;
import engine.structure.Entity;
import engine.structure.Scene;
import game.scene.Battlefield;
import testing.MaterialTest;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.opengl.GL11.*;

public class Runtime {
    public static Display display;
    public static Scene currentScene = new Battlefield();
    public static FrameBuffer frameBuffer;
    public static boolean isRunning = false;
    public static int currentTileId;
    public static int currentEntityId;

    private static float beginTime = 0, endTime = 0, startTime = 0;

    public static void start(Display display, Scene scene){
        Runtime.display = display;

        PlatformResources.getSystemStats();

        startTime = (float)System.nanoTime();

        currentScene = scene;

        currentScene.load();

        for(Instance instance : currentScene.instances){
            instance.loadMesh();
        }
        for(Entity entity : currentScene.entities.values()){
            entity.start();
            if(entity.meshInstance != null) {
                entity.meshInstance.loadMeshes();
            }
        }

        frameBuffer = new FrameBuffer(display.width / Globals.resolution, display.height / Globals.resolution);

        RenderQuad.prepare();

        Globals.load();

        GuiLayer.load();

        isRunning = true;
    }

    public static void loop(){
        currentScene.update();
        currentScene.camera.calculateMatrix();
        currentScene.sun.calculateMatrix();

        frameBuffer.bind();

        glEnable(GL_DEPTH_TEST);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for(Entity entity : currentScene.entities.values()){
            entity.update();
            if(entity.meshInstance != null) {
                entity.meshInstance.sendToRender();
            }
        }
        for(Instance instance : currentScene.instances){
            instance.sendToRender(currentScene.camera.view);
        }

        if(MouseManager.isButtonDown(GLFW_MOUSE_BUTTON_1)){
            currentEntityId = frameBuffer.sampleId();
        }

        currentTileId = frameBuffer.sampleTile();

        frameBuffer.unbind();

        frameBuffer.blit(display.width, display.height);

        glDisable(GL_DEPTH_TEST);

        EntityRenderer.lightingPass(frameBuffer);

        GuiLayer.draw();
    }

    public static void end(){
        currentScene.end();
        for(Entity entity : currentScene.entities.values()){
            entity.end();
        }
    }
}
