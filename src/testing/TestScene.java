package testing;

import engine.core.Globals;
import engine.graphics.Instance;
import engine.importer.InstanceImporter;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import engine.structure.Scene;
import engine.types.Transform;
import game.entities.Tank;
import org.joml.Vector3f;
import testing.entities.TestEntity;

public class TestScene extends Scene {

    @Override
    public void load() {
        super.addEntity(new Tank());
    }

    @Override
    public void update() {
    }

    @Override
    public void end() {

    }
}
