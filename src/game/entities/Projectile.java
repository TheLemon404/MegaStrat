package game.entities;

import engine.core.Globals;
import engine.core.Runtime;
import engine.importer.EntityImporter;
import engine.structure.Entity;
import engine.types.ImageTexture;
import org.joml.Vector3f;

import javax.swing.text.Style;

public class Projectile extends Entity {

    public Vector3f position, force;
    // ^----- Position and Force Vectors
    public boolean loaded = false;
    // ^----- Determines if mesh has been loaded yet

    public Projectile(){

        position = new Vector3f();
        force = new Vector3f();

        if(Runtime.isRunning) {
            load();
        }
    }
    public Projectile(Vector3f _position, Vector3f _force){

        position = _position;
        force = _force;

        if(Runtime.isRunning) {
            load();
        }
    }

    public void load(){
        if (loaded == false) {
            // Loading has Started -- Locks Load System from repeating
            loaded = true;
            // Instantiates new Mesh
            meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/misc/cube.fbx", Globals.entityShader, this);
            // Adds black Text to Mesh
            meshInstance.meshes.get(0).material.texture = new ImageTexture("src/resources/textures/misc/black.png");

            meshInstance.transform.rotation.x = (float) Math.toRadians(0);
            meshInstance.transform.scale = new Vector3f(0.1f, 0.1f, 0.1f);
            meshInstance.collider.radius = 0.1f;

            position.y = position.y + meshInstance.collider.radius*1.05f;
            meshInstance.transform.position = position;
            meshInstance.hasPhysics = true;
            meshInstance.linearVelocity = meshInstance.linearVelocity.add(force);
            meshInstance.bounceCoefficient = 0.5f;
            meshInstance.frictionCoefficient = 0.5f;
            meshInstance.shadowSize = 0.2f;
            meshInstance.weight = 5;
        }
    }
    @Override
    public void start() {
        load();
    }

    @Override
    public void update() {
    }

    @Override
    public void end() {

    }
}
