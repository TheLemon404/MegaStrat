package engine.graphics;

import engine.core.Globals;
import engine.types.Transform;
import engine.utils.Algorythms;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Random;

public class Instance {
    public Mesh mesh = new Mesh();
    public ArrayList<Material> materials = new ArrayList<>();
    public ArrayList<Transform> transforms = new ArrayList<>();
    public ArrayList<Integer> ids = new ArrayList<>();
    public Shader shader;

    public Instance(){
        this.shader = Globals.instanceShader;
        shader.compile();
    }

    public void addTransformWithId(Transform transform){
        transforms.add(transform);
        ids.add(Algorythms.generateId(10000, 99999));
    }

    public Transform getTransformFromId(int id){
        for(int i = 0; i < transforms.size(); i++) {
            if(ids.get(i) == id) {
                return transforms.get(i);
            }
        }

        return null;
    }

    public void loadMesh(){
        for(int i = 0; i < transforms.size(); i++){
            ids.add(Algorythms.generateId(10000, 99999));
        }
        mesh.load();
    }

    public void sendToRender(Matrix4f view){
        for(Transform t : transforms){
            t.calculateMatrix();
        }
        InstanceRenderer.submit(shader, this, view);
    }
}
