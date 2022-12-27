package engine.structure;

import engine.graphics.Instance;
import org.joml.Vector3f;

import java.util.HashMap;

public abstract class Scene {
    public Vector3f lightDirection = new Vector3f(-0.3f, -1, -0.7f);
    public Camera camera = new Camera();

    public HashMap<Integer, Entity> entities = new HashMap<>();

    public HashMap<Integer, Instance> instances = new HashMap<>();

    protected void addEntity(Entity entity){
        entities.put(entity.id, entity);
    }

    protected void addInstance(Instance instance){
        instances.put(instance.id, instance);
    }

    public Entity getEntity(int id){
        return entities.get(id);
    }

    public Instance getInstance(int id){
        return instances.get(id);
    }

    public abstract void load();
    public abstract void update();
    public abstract void end();
}
