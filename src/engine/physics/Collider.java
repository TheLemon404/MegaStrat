package engine.physics;

import engine.core.Runtime;
import engine.core.SceneRuntime;
import engine.structure.Entity;
import engine.types.Transform;

public class Collider {
    public float radius = 1;
    public Transform transform;

    public Collider(Transform transform){
        this.transform = transform;
    }

    public Collider(Transform transform, float radius){
        this.radius = radius;
        this.transform = transform;
    }

    public boolean isCollidingWithGround(){
        if(transform.position.y - radius <= 0){
            return true;
        }
        return false;
    }

    public boolean isCollidingWithCollider(){
        for(Entity entity : SceneRuntime.currentScene.entities.values()) {
            if(entity.meshInstance.hasPhysics){
                if(entity.meshInstance.transform.position.distance(transform.position) <= entity.meshInstance.collider.radius + radius){
                    return true;
                }
            }
        }
        return false;
    }
}
