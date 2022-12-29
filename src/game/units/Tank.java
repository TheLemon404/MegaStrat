package game.units;

import engine.core.Globals;
import engine.importer.EntityImporter;
import engine.structure.Entity;
import org.joml.Vector3f;

public class Tank extends Entity {

    @Override
    public void start() {
        meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/tank.fbx", Globals.entityShader, this);
        meshInstance.transform.rotation.x = (float)Math.toRadians(-90);
        meshInstance.transform.scale = new Vector3f(0.1f, 0.1f, 0.1f);
    }

    @Override
    public void update() {
    }

    @Override
    public void end() {

    }
}
