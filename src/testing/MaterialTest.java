package testing;

import engine.core.Globals;
import engine.importer.EntityImporter;
import engine.structure.Blank;
import engine.structure.Entity;
import engine.structure.Scene;
import game.entities.Tank;
import org.joml.Vector3f;

public class MaterialTest extends Scene {

    Entity cube = new Blank();
    Entity sphere = new Blank();
    @Override
    public void load() {
        cube.meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/misc/cube.fbx", Globals.entityShader, cube);
        cube.meshInstance.meshes.get(0).material.color = new Vector3f(0.8f, 0.2f, 0.2f);

        sphere.meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/misc/sphere.fbx", Globals.entityShader, sphere);
        sphere.meshInstance.meshes.get(0).material.color = new Vector3f(0.2f, 0.5f, 0.8f);
        sphere.meshInstance.transform.position.x = 5;

        super.addEntity(cube);
        super.addEntity(sphere);
    }

    @Override
    public void update() {
    }

    @Override
    public void end() {

    }
}
