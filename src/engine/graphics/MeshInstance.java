package engine.graphics;

import engine.types.Transform;

import java.util.ArrayList;

public class MeshInstance {
    public Transform transform = new Transform();
    public ArrayList<Mesh> meshes = new ArrayList<>();
    private Shader shader;

    public void addMesh(Mesh mesh){
        meshes.add(mesh);
    }

    public MeshInstance(Shader shader){
        this.shader = shader;
    }

    public void loadMeshes(){
        for(Mesh mesh : meshes){
            mesh.load();
        }
    }

    public void sendToRender(){
        for(Mesh mesh : meshes){
            transform.calculateMatrix(mesh.subRotation);
            EntityRenderer.submit(shader, mesh, transform);
        }
    }
}
