package game.entities;

import engine.core.Globals;
import engine.core.Runtime;
import engine.importer.EntityImporter;
import engine.structure.Entity;
import org.joml.Vector3f;

public class Projectile extends Entity {
    public Projectile(Vector3f position, Vector3f force){
        if(Runtime.isRunning){
            load();
        }
    }
    public Projectile(Vector3f position){
        if(Runtime.isRunning){
            load();
        }
    }

    public void load(){
        meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/misc/cube.fbx", Globals.entityShader, this);

        meshInstance.hasPhysics = true;
        meshInstance.linearVelocity = new Vector3f(0.1f,0.01f,0);
        meshInstance.transform.position.y = 1;
        meshInstance.transform.rotation.x = (float)Math.toRadians(-90);
        meshInstance.transform.scale = new Vector3f(0.5f, 0.05f, 0.05f);
    }
    @Override
    public void start() {
        load();
    }

    @Override
    public void update() {
        if(Runtime.currentEntityId == super.id){
            meshInstance.hasShadow = false;
        }
        else{
            meshInstance.hasShadow = true;
        }

        //Movement - Random



    }

    @Override
    public void end() {

    }
}
