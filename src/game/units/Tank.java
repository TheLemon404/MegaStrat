package game.units;

import engine.core.Globals;
import engine.core.Runtime;
import engine.events.MouseManager;
import engine.events.KeyboardManager;
import engine.graphics.ParticleInstance;
import engine.importer.EntityImporter;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import engine.types.Transform;
import org.joml.Vector3f;
import java.lang.Math;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;


public class Tank extends Entity {

    public Vector3f position, force;
    // ^----- Position and Force Vectors
    public boolean loaded = false;
    // ^----- Determines if mesh has been loaded yet
    public Transform wantedTile;
    // ^----- End goal of tank when moving
    private boolean mouseDownCurrently = false;
    // ^---- Tells if the the mouse button 1 is down, used to determine new Click or button held down

    public Tank(Vector3f _position, Vector3f _force){
        // Instantiated with Position and Force Data
        position = _position;
        force = _force;
        if(Runtime.isRunning){
            load();
        }
    }
    public Tank(Vector3f _position){
        // Instantiated with Position Data
        position = _position;
        force = new Vector3f();
        if(Runtime.isRunning){
            load();
        }
    }
    public Tank(){
        // New Instantiation without any Data
        position = new Vector3f();
        force = new Vector3f();
        if(Runtime.isRunning){
            load();
        }
    }
    public void load() {
        if (!loaded) {
            loaded = true;
            meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/tank.fbx", Globals.entityShader, this);

            meshInstance.hasPhysics = true;
            meshInstance.collider.radius = 0;
            meshInstance.transform.position.y = 0;

            meshInstance.transform.rotation.x = (float) Math.toRadians(-90);
            meshInstance.transform.scale = new Vector3f(0.1f, 0.1f, 0.1f);
            meshInstance.bounceCoefficient = 0.2f;
            meshInstance.weight = 100;
        }
    }

    @Override
    public void start() {
        load();
    }

    @Override
    public void update() {
        // Checks if mouse was down in previous frame to determine if new click or hold
        boolean newClick = false;
        if(MouseManager.isButtonDown(GLFW_MOUSE_BUTTON_1)){
            if (!mouseDownCurrently) {
                // Mouse was NOT down 1 frame before this, so this is a NEW Click
                newClick = true;
                mouseDownCurrently = true;
            }
        } else {
            mouseDownCurrently = false;
        }




        // Visual update ------------------------------------
        // Point in direction of movement vector if Movement is large enough
        float movementScalar = (float)Math.sqrt(Math.pow(meshInstance.linearVelocity.x, 2) + Math.pow(meshInstance.linearVelocity.z, 2));
        // Check if Movement is enough for visual update
        if (movementScalar > 1) {
            // Computing the angle or direction of movement in terms of degrees
            float directionOfMovement = (float) Math.toDegrees(Math.acos(meshInstance.linearVelocity.x / movementScalar));
            // Adding angle offset based on situation
            if (meshInstance.linearVelocity.x > 0 ) {
                if (meshInstance.linearVelocity.z <= 0) {
                    directionOfMovement += 90;
                }
            }else if (meshInstance.linearVelocity.x < 0 ) {
                if (meshInstance.linearVelocity.z <= 0) {
                    directionOfMovement += 90;
                } else {
                    directionOfMovement += 180;
                }
            } else {
                if (meshInstance.linearVelocity.z < 0) {
                    directionOfMovement += 90;
                } else {
                    directionOfMovement -= 90;
                }
            }
            // Convert to Radians
            directionOfMovement = (float)Math.toRadians(directionOfMovement);
            // Update Rotation
            meshInstance.transform.rotation.z = directionOfMovement;
        }


    }

    @Override
    public void end() {

    }
}
