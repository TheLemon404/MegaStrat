package engine.core;

import engine.events.MouseManager;
import engine.graphics.Instance;
import engine.graphics.ParticleInstance;
import engine.importer.MapImporter;
import engine.structure.Entity;
import engine.structure.Scene;
import game.scene.Battlefield;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class SceneRuntime {
    public static Scene currentScene = new Battlefield();

    public static void load(Scene scene){
        //currentScene = scene;

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
        for(ParticleInstance particle : currentScene.particles.values()){
            particle.loadMesh();
        }

        try {
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void update(){
        currentScene.update();
        currentScene.camera.calculateMatrix();
        currentScene.sun.calculateMatrix();

        for(Entity entity : currentScene.entities.values()){
            entity.update();
            if(entity.meshInstance != null) {
                entity.meshInstance.sendToRender();
            }
        }
        for(Instance instance : currentScene.instances){
            instance.sendToRender(currentScene.camera.view);
        }
        for(ParticleInstance particle : currentScene.particles.values()){
            particle.sendToRender(currentScene.camera.view);
        }
    }

    public static void end(){
        currentScene.end();
        for(Entity entity : currentScene.entities.values()){
            entity.end();
        }
    }
}
