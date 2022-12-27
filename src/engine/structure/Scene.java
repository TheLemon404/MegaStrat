package engine.structure;

import java.util.HashMap;

public abstract class Scene {
    public Camera camera = new Camera();

    public HashMap<Integer, Entity> entities = new HashMap<>();

    protected void addEntity(Entity entity){
        entities.put(entity.id, entity);
    }

    public Entity getEntity(int id){
        return entities.get(id);
    }

    public abstract void load();
    public abstract void update();
    public abstract void end();
}
