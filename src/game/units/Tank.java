package game.units;

import engine.core.Globals;
import engine.core.Runtime;
import engine.events.KeyboardManager;
import engine.graphics.ParticleInstance;
import engine.importer.EntityImporter;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import org.joml.Vector3f;
import java.lang.Math;

public class Tank extends Entity {

    public Vector3f position, force;
    // ^----- Position and Force Vectors
    public boolean loaded = false;
    // ^----- Determines if mesh has been loaded yet

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
        if (loaded == false) {
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
        // Point in direction of movement vector if Movement is large enough
        if (Math.sqrt(Math.pow(meshInstance.linearVelocity.x, 2) + Math.pow(meshInstance.linearVelocity.y, 2)) > 0.0001) {
            meshInstance.transform.rotation.z = (float)Math.toRadians(meshInstance.linearVelocity.x);
        }


    }

    @Override
    public void end() {

    }
}
