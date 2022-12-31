package engine.graphics;

import engine.core.Globals;
import engine.core.Runtime;
import engine.types.Transform;
import engine.utils.Algorythms;
import engine.utils.MathTools;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class ParticleInstance {
    public Mesh mesh = new Mesh();
    public Material material = new Material();
    public Transform transform = new Transform();
    public Vector3f scale = new Vector3f(0.1f, 0.1f, 0.1f);
    public ArrayList<Vector3f> positions = new ArrayList<>();
    public int id = 0;
    public int count = 10;
    public float cutoff = 5;
    public Shader shader;

    public ParticleInstance(Mesh mesh){
        this.shader = Globals.particleShader;
        this.mesh = mesh;
        if(Runtime.isRunning){
            loadMesh();
        }
    }

    public void loadMesh(){
        for(int i = 0; i < count; i++){
            positions.add(new Vector3f(0, -i * 2, 0));
        }
        transform.position.y = 0.2f;
        id = Algorythms.generateId(10, 99);
        mesh.load();
    }

    public void sendToRender(Matrix4f view){

        for(Vector3f position : positions){
            if(position.y >= cutoff){
                position.y = transform.position.y;
            }
            else{
                position.y += 0.04f;
            }
        }

        transform.scale = scale;
        transform.calculateMatrix();
        ParticleRenderer.submit(shader, this, view);
    }
}
