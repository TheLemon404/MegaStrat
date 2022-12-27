package engine.structure;

import engine.graphics.MeshInstance;

import java.util.Random;

public abstract class Entity {
    public MeshInstance meshInstance;
    public int id;
    public Entity(){
        id = new Random().nextInt(0, 99999);
    }
    public abstract void start();
    public abstract void update();
    public abstract void end();
}
