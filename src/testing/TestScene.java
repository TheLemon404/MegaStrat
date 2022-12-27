package testing;

import engine.graphics.Mesh;
import engine.graphics.MeshInstance;
import engine.graphics.Shader;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import engine.structure.Scene;
import testing.entities.TestEntity;

public class TestScene extends Scene {
    @Override
    public void load() {
        Entity e = new TestEntity();
        e.meshInstance = MeshImporter.loadMeshFromFile("src/resources/meshes/sphere.fbx", new Shader("src/resources/shaders/flat.glsl"));
        super.addEntity(e);
    }

    @Override
    public void update() {
    }

    @Override
    public void end() {

    }
}
