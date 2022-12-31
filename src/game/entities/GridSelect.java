package game.entities;

import engine.core.Globals;
import engine.core.Runtime;
import engine.core.SceneRuntime;
import engine.events.MouseManager;
import engine.graphics.EntityRenderer;
import engine.graphics.Instance;
import engine.graphics.Mesh;
import engine.importer.EntityImporter;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import engine.structure.Scene;
import engine.types.ImageTexture;
import engine.types.Transform;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class GridSelect extends Entity {
    private Mesh tileMarker;
    private int selectedId = 0;
    private Transform markerTransform = new Transform();

    @Override
    public void start() {
        super.id = 1;
        tileMarker = MeshImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx");
        meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx", Globals.entityShader, this);
        meshInstance.meshes.get(0).material.texture = new ImageTexture("src/resources/textures/gameplay/grid_select.png");
        meshInstance.meshes.get(0).material.strength = 10;
        meshInstance.hasShadow = false;
        meshInstance.transform.position.y = 0.001f;
        tileMarker.material.color = new Vector3f(0.2f, 0.5f, 0.9f);
        tileMarker.load();
    }

    @Override
    public void update() {
        Transform t = getInstanceFromTileId(SceneRuntime.currentTileId);
        if(MouseManager.isButtonDown(GLFW_MOUSE_BUTTON_1) && selectedId != SceneRuntime.currentTileId && SceneRuntime.currentTileId != 0){
            selectedId = SceneRuntime.currentTileId;
        }
        if(selectedId != 0){
            Vector3f pos = getInstanceFromTileId(selectedId).position;
            markerTransform.position.x = pos.x;
            markerTransform.position.y = 0.0005f;
            markerTransform.position.z = pos.z;
            markerTransform.calculateMatrix();
            EntityRenderer.submit(Globals.entityShader, tileMarker, markerTransform, 0);
        }
        if(t != null) {
            meshInstance.transform.position = new Vector3f(t.position.x, 0.001f, t.position.z);
        }
    }

    private Transform getInstanceFromTileId(int id){
        Instance i = null;
        for(Instance instance : SceneRuntime.currentScene.instances){
            if(instance.getTransformFromId(id) != null){
                i = instance;
            }
        }
        if(i != null) {
            return i.getTransformFromId(id);
        }
        return null;
    }

    @Override
    public void end() {

    }
}
