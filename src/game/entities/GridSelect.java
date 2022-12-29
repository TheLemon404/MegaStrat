package game.entities;

import engine.core.Globals;
import engine.core.Runtime;
import engine.graphics.Instance;
import engine.importer.EntityImporter;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import engine.types.ImageTexture;
import engine.types.Transform;
import game.instances.Terrain;
import org.joml.Vector3f;

public class GridSelect extends Entity {
    @Override
    public void start() {
        super.id = 1;
        meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx", Globals.entityShader, this);
        meshInstance.meshes.get(0).material.texture = new ImageTexture("src/resources/textures/gameplay/grid_select.png");
        meshInstance.meshes.get(0).material.strength = 10;
        meshInstance.hasShadow = false;
        meshInstance.transform.position.y = 0.001f;
    }

    @Override
    public void update() {
        Transform t = getInstanceFromTileId(Runtime.currentTileId);
        if(t != null) {
            meshInstance.transform.position = new Vector3f(t.position.x, 0.001f, t.position.z);
        }
    }

    private Transform getInstanceFromTileId(int id){
        Instance i = null;
        for(Instance instance : Runtime.currentScene.instances){
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
