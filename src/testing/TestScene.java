package testing;

import engine.graphics.Mesh;
import engine.graphics.MeshInstance;
import engine.graphics.Shader;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import engine.structure.Scene;
import org.joml.Vector3f;
import testing.entities.TestEntity;

public class TestScene extends Scene {

    Entity e = new TestEntity();
    float i = 0;
    @Override
    public void load() {
        e.meshInstance = MeshImporter.loadMeshFromFile("src/resources/meshes/cube.fbx", new Shader("src/resources/shaders/entity.glsl"));
        e.meshInstance.transform.scale = new Vector3f(0.2f, 0.2f, 0.2f);
        super.addEntity(e);
    }

    @Override
    public void update() {

    }

    @Override
    public void end() {

    }
}
