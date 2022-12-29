package engine.graphics;

import engine.core.Globals;
import engine.core.Runtime;
import engine.importer.MeshImporter;
import engine.physics.Raycast;
import engine.structure.Entity;
import engine.types.ImageTexture;
import engine.types.Transform;
import org.joml.Vector3f;

import java.util.ArrayList;

public class MasterMesh {
    public Transform transform = new Transform();
    public ArrayList<Mesh> meshes = new ArrayList<>();
    public Mesh shadow;
    public boolean hasShadow = true;
    public boolean squareShadow = false;
    public Transform shadowTransform = new Transform();
    private Entity entity;
    private Shader shader;

    public void addMesh(Mesh mesh){
        meshes.add(mesh);
    }

    public MasterMesh(Shader shader, Entity entity){
        this.shader = shader;
        this.entity = entity;
    }

    public void loadMeshes(){
        if(hasShadow){
            if(squareShadow){
                shadow = MeshImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx", Globals.entityShader);
            }
            else {
                shadow = MeshImporter.loadMeshFromFile("src/resources/meshes/misc/circle.fbx", Globals.entityShader);
            }
            shadow.material.texture = new ImageTexture("src/resources/textures/misc/white.png");
            shadow.material.color = new Vector3f(0.2f, 0.5f, 0.4f);
            shadow.load();
        }
        for(Mesh mesh : meshes){
            mesh.load();
        }
    }

    public void sendToRender(){
        //do all updates here

        calculateShadow();
        if(hasShadow){
            EntityRenderer.submit(shader, shadow, shadowTransform, 0);
        }
        for(Mesh mesh : meshes){
            transform.calculateMatrix(mesh.subRotation);
            EntityRenderer.submit(shader, mesh, transform, entity.id);
        }
    }

    private void calculateShadow(){
        shadowTransform.position = new Vector3f(transform.position.x, 0.001f, transform.position.z);
        shadowTransform.scale = new Vector3f(0.2f, 0.2f, 0.2f);
        shadowTransform.calculateMatrix();
    }
}
