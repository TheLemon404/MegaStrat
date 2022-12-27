package engine.structure;

import engine.graphics.MeshInstance;

public abstract class Entity {
    public MeshInstance meshInstance;
    public int id;
    public abstract void start();
    public abstract void update();
    public abstract void end();
}
