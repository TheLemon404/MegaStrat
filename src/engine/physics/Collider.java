package engine.physics;

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
}
