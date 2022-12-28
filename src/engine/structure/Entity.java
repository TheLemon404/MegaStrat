package engine.structure;

import engine.graphics.MasterMesh;
import engine.utils.Algorythms;
import engine.utils.MathTools;

import java.util.Random;

public abstract class Entity {
    public MasterMesh meshInstance;
    public int id;
    public Entity(){
        id = Algorythms.generateId(10000, 99999);
    }
    public abstract void start();
    public abstract void update();
    public abstract void end();
}
