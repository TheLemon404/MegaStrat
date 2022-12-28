package game.scene;

import engine.core.Globals;
import engine.importer.EntityImporter;
import engine.structure.Blank;
import engine.structure.Entity;
import engine.structure.Scene;
import game.entities.Tank;
import game.instances.Terrain;
import org.joml.Vector3f;

public class Battlefield extends Scene {
    @Override
    public void load() {
        new Terrain(this, 10, 0, 0);
        new Terrain(this, 10, 1, 1);
        new Terrain(this, 10, 0, 1);
        new Terrain(this, 10, 1, 0);

        super.addEntity(new Tank());
    }

    @Override
    public void update() {

    }

    @Override
    public void end() {

    }
}
