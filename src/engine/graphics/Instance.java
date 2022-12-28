package engine.graphics;

import engine.types.Transform;
import engine.utils.Algorythms;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Random;

public class Instance {
    public Mesh mesh = new Mesh();
    public ArrayList<Material> materials = new ArrayList<>();
    public ArrayList<Transform> transforms = new ArrayList<>();
    public int id;
    public Shader shader;

    public Instance(Shader shader){
        id = Algorythms.generateId(1000, 9999);
        this.shader = shader;
        shader.compile();
    }

    public void loadMesh(){
        mesh.load();
    }

    public void sendToRender(Matrix4f view){
        for(Transform t : transforms){
            t.calculateMatrix();
        }
        InstanceRenderer.submit(shader, this, view);
    }
}
