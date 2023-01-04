package engine.graphics;

import engine.core.Globals;
import engine.core.Runtime;
import engine.core.SceneRuntime;
import engine.structure.Entity;
import engine.types.Transform;
import engine.utils.Algorythms;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Instance {
    public Mesh mesh = new Mesh();
    public ArrayList<Material> materials = new ArrayList<>();
    public ArrayList<Transform> transforms = new ArrayList<>();
    public ArrayList<Integer> ids = new ArrayList<>();
    public HashMap<Integer, Boolean> visibility = new HashMap<>();
    public Shader shader;

    public Instance(){
        this.shader = Globals.instanceShader;
        shader.compile();
    }

    public void addTransformWithId(Transform transform){
        transforms.add(transform);
        int id = Algorythms.generateId(10000, 99999);
        visibility.put(id, false);
        ids.add(id);
    }

    public Transform getTransformFromId(int id){
        for(int i = 0; i < transforms.size(); i++) {
            if(ids.get(i) == id) {
                return transforms.get(i);
            }
        }

        return null;
    }

    public int getIdFromTransform(Transform transform){
        for(int i = 0; i < transforms.size(); i++) {
            if(transforms.get(i) == transform) {
                return ids.get(i);
            }
        }

        return 0;
    }

    public void loadMesh(){
        for(int i = 0; i < transforms.size(); i++){
            ids.add(Algorythms.generateId(10000, 99999));
        }
        mesh.load();
    }

    public void sendToRender(Matrix4f view){
        for(Entity entity : SceneRuntime.currentScene.entities.values()){
            for(Transform transform : transforms){
                //checks if the distance from the entity and tile is within the entities view
                if(entity.meshInstance.transform.position.distance(transform.position) < entity.viewDistance){
                    int id = getIdFromTransform(transform);
                    //sets the tile as visible
                    visibility.computeIfPresent(id, (key, old) -> true);
                }
            }
        }
        for(Transform t : transforms){
            t.calculateMatrix();
        }
        InstanceRenderer.submit(shader, this, view);
    }
}
