package engine.gui;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Rect {
    public Vector2f position = new Vector2f(10, 10), scale = new Vector2f(5, 5);
    public float rotation = 0;
    public Matrix4f matrix = new Matrix4f();

    public void calculateMatrix(){
        matrix.identity();
        matrix.translate(new Vector3f(position.x, position.y, 0));
        matrix.rotateAffineXYZ(0, 0, (float)Math.toRadians(rotation));
        matrix.scale(new Vector3f(scale.x, scale.y, 0));
    }

    public void calculateMatrix(Vector2f position, float rotation, Vector2f scale){
        matrix.identity();
        matrix.translate(new Vector3f(this.position.x + position.x, this.position.y + position.y, 0));
        matrix.rotateAffineXYZ(0, 0, (float)Math.toRadians(this.rotation + rotation));
        matrix.scale(new Vector3f(scale.x, scale.y, 0));
    }
}
