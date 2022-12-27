package engine.types;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    public Vector3f position = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public Vector3f scale = new Vector3f(1,1,1);
    public Matrix4f matrix = new Matrix4f();

    public void calculateMatrix(){
        matrix.identity();
        matrix.translate(position);
        matrix.rotateAffineXYZ(rotation.x, rotation.y, rotation.z);
        matrix.scale(scale);
    }
}
