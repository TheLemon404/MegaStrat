package engine.graphics;

import engine.types.Transform;

import java.util.ArrayList;
import java.util.Random;

public class Instance {
    public Mesh mesh = new Mesh();
    public ArrayList<Transform> transforms = new ArrayList<>();
    public int id;
    public Shader shader;

    public Instance(Shader shader){
        id = new Random().nextInt(0, 99999);
        this.shader = shader;
        shader.compile();
    }

    public void loadMesh(){
        mesh.load();
    }

    public void sendToRender(){
        for(Transform t : transforms){
            t.calculateMatrix();
        }
        InstanceRenderer.submit(shader, this);
    }
}
