package engine.graphics;

import engine.core.Globals;
import engine.importer.MeshImporter;
import engine.physics.Collider;
import engine.structure.Entity;
import engine.types.ImageTexture;
import engine.types.Transform;
import org.joml.Vector3f;

import javax.swing.text.Style;
import java.lang.Math;

import java.util.ArrayList;

public class MasterMesh {
    private float gravityScale = 9.8f;
    // ^--- How much does gravity pull down on Entity
    public Vector3f linearVelocity = new Vector3f();
    // ^--- Force Vector
    public float frictionCoefficient = 0.1f;
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
    public boolean hasShadow = true;
    // ^--- Determines if Entity has Shadow
    public boolean squareShadow = false;
    // ^--- Determines shape of shadow, Default: Circle
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
                if (Math.abs(linearVelocity.y) < 0.05f*bounceCoefficient) {
                    linearVelocity.y = 0;
                }
                System.out.println(linearVelocity.y);

                // On Ground, Friction is applied
                // Checks if Entity's Current force breaks Kinetic (Moving) Friction coefficient
                if (Math.sqrt(Math.pow(linearVelocity.x, 2) + Math.pow(linearVelocity.y, 2)) < (weight * frictionCoefficient) / 1000) {
                    // Couldn't break friction
                    linearVelocity.x = 0;
                    linearVelocity.z = 0;

                } else {
                    // Broke friction, has reduced friction has little effect
                    linearVelocity.x = linearVelocity.x * (1-frictionCoefficient);
                    linearVelocity.z = linearVelocity.z *(1-frictionCoefficient);
                }

            } else {
                // Entity has not collided with Ground, must still be in Air
                linearVelocity.y -= gravityScale / 1000;
            }
            // Update position of Entity
            transform.position.x += linearVelocity.x;
            transform.position.y += linearVelocity.y;
            transform.position.z += linearVelocity.z;
        }
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
