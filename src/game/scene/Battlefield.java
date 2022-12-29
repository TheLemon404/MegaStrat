package game.scene;

import engine.structure.Scene;
import game.entities.GridSelect;
import game.units.Tank;
import game.instances.Terrain;

public class Battlefield extends Scene {
    @Override
    public void load() {
        new Terrain(this, 10, 0, 0);
        new Terrain(this, 10, 1, 1);
        new Terrain(this, 10, 0, 1);
        new Terrain(this, 10, 1, 0);

        new Terrain(this, 10, 2, 2);
        new Terrain(this, 10, 3, 3);
        new Terrain(this, 10, 2, 3);
        new Terrain(this, 10, 3, 2);

        super.addEntity(new GridSelect());

        super.addEntity(new Tank());
    }

    @Override
    public void update() {

    }

    @Override
    public void end() {

    }
}
