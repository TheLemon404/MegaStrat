package engine.graphics;

import engine.core.Globals;
import engine.core.Runtime;
import engine.core.SceneRuntime;
import engine.importer.MeshImporter;
import engine.physics.Collider;
import engine.structure.Entity;
import engine.types.ImageTexture;
import engine.types.Transform;
import engine.utils.MathTools;
import org.joml.Vector3f;

import javax.swing.text.Style;
import java.lang.Math;

import java.util.ArrayList;

public class MasterMesh {
    private float gravityScale = 9.8f;
    // ^--- How much does gravity pull down on Entity
    public Vector3f linearVelocity = new Vector3f();
    // ^--- Force Vector
    public float frictionCoefficient = 1f;
    // ^--- How much Friction between Entity and other Entities or Ground

    public float airResistanceCoefficient = 0.05f;
    // ^--- How much Friction between Entity and Air, basically Friction when not touching Ground
    public float bounceCoefficient = 0;
    // ^--- How Bouncy the Entity is

    public float weight = 1;
    // ^--- Weight of Entity

    public Transform transform = new Transform();
    public ArrayList<Mesh> meshes = new ArrayList<>();
    public Mesh shadow;
    public Mesh selectedMesh;
    public boolean selectable = true;
    public boolean selected = false;
    // ^--- Determines if Entity has been selected by mouse
    public boolean hasShadow = true;
    // ^--- Determines if Entity has Shadow
    public boolean squareShadow = false;
    // ^--- Determines shape of shadow, Default: Circle
    public float shadowSize = 0.2f;
    // ^--- Determines size of shadow, Default: 0.2 - base for Tank - Setting to 1 = full tile
    public boolean hasPhysics = false;
    // ^--- Determines whether gravity effects Entity
    public Transform shadowTransform = new Transform();
    private Entity entity;
    private Shader shader;
    public Collider collider;
    // ^--- Used to detect Collisions

    public void addMesh(Mesh mesh){
        meshes.add(mesh);
    }

    public MasterMesh(Shader shader, Entity entity){
        this.shader = shader;
        this.entity = entity;

        collider = new Collider(transform, 0);
    }

    public void loadMeshes(){
        if(hasShadow){
            if(squareShadow){
                shadow = MeshImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx");
            }
            else {
                shadow = MeshImporter.loadMeshFromFile("src/resources/meshes/misc/circle.fbx");
            }
            shadow.material.texture = new ImageTexture("src/resources/textures/misc/white.png");
            shadow.material.color = new Vector3f(0.2f, 0.5f, 0.4f);
            shadow.load();
        }
        selectedMesh = MeshImporter.loadMeshFromFile("src/resources/meshes/misc/circle.fbx");
        selectedMesh.material.color = new Vector3f(0.2f, 0.5f, 0.9f);
        selectedMesh.load();
        for(Mesh mesh : meshes){
            mesh.load();
        }
    }

    public void sendToRender(){
        //checks if entity is selected
        if(SceneRuntime.currentEntityId == entity.id && selectable){
            selected = true;
        }
        else{
            selected = false;
        }

        // Checks whether gravity effects Entity
        if(hasPhysics) {
            // Gravity Effect Entity

            // Checks if colliding into Ground
            if (collider.isCollidingWithGround()) {
                // Entity has collided with Ground

                // Was Falling?
                if ( linearVelocity.y < 0) {
                    // Stops Entity Velocity on Y-Axis
                    linearVelocity.y = -linearVelocity.y * bounceCoefficient;
                    // Stops Entity from falling past Ground
                    transform.position.y = collider.radius;
                }
                if (Math.abs(linearVelocity.y) < 0.1f*bounceCoefficient) {
                    linearVelocity.y = 0;
                }

                // On Ground, Friction is applied
                // Checks if Entity's Current force breaks Kinetic (Moving) Friction coefficient
                if (Math.sqrt(Math.pow(linearVelocity.x, 2) + Math.pow(linearVelocity.y, 2)) < (weight * frictionCoefficient) / 1000) {
                    // Couldn't break friction
                    linearVelocity.x = 0;
                    linearVelocity.z = 0;

                } else {
                    // Broke friction, has reduced friction has little effect
                    //                   Gets Direction in 1 or -1     multiplied by Absolute Value of velocity with resistance force subtracted from it
                    if (Math.abs(linearVelocity.x)- (frictionCoefficient*weight/100) > 0) {
                        linearVelocity.x = (linearVelocity.x / Math.abs(linearVelocity.x)) * Math.abs(linearVelocity.x) - (frictionCoefficient * weight/100);
                    }else {
                        linearVelocity.x = 0;
                    }
                    if (Math.abs(linearVelocity.z)- (frictionCoefficient*weight/100) > 0) {
                        linearVelocity.z = (linearVelocity.z / Math.abs(linearVelocity.z)) * Math.abs(linearVelocity.z) - (frictionCoefficient * weight/100);
                    }else {
                        linearVelocity.z = 0;
                    }
                }
            } else {
                // Entity has not collided with Ground, must still be in Air
                linearVelocity.y -= gravityScale / 1000*weight;

            }
            // Update position of Entity
            transform.position.y += linearVelocity.y / weight * Globals.deltaTime;
            transform.position.x += linearVelocity.x / weight * Globals.deltaTime;
            transform.position.z += linearVelocity.z / weight * Globals.deltaTime;
        }
        calculateShadow();
        if(hasShadow && !selected){
            EntityRenderer.submit(shader, shadow, shadowTransform, 0);
        }
        if(selected){
            EntityRenderer.submit(shader, selectedMesh, shadowTransform, 0);
        }
        for(Mesh mesh : meshes){
            transform.calculateMatrix(mesh.subRotation);
            EntityRenderer.submit(shader, mesh, transform, entity.id);
        }
    }

    private void calculateShadow(){
        shadowTransform.position = new Vector3f(transform.position.x, 0.001f, transform.position.z);
        float size = shadowSize - (transform.position.y- collider.radius) * 0.5f;
        if (size < 0) {
            size = 0;
        }
        shadowTransform.scale = new Vector3f(size, size, size);
        shadowTransform.calculateMatrix();
    }
}

