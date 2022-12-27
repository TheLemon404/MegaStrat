package game.entities;

import engine.core.Globals;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import org.joml.Vector3f;

public class Tank extends Entity {

    @Override
    public void start() {
        meshInstance = MeshImporter.loadMeshFromFile("src/resources/meshes/tank.fbx", Globals.entityShader);
        meshInstance.transform.rotation.x = (float)Math.toRadians(-90);
        meshInstance.transform.scale = new Vector3f(0.3f, 0.3f, 0.3f);
    }

    @Override
    public void update() {
    }

    @Override
    public void end() {

    }
}
