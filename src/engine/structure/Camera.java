package engine.structure;

import engine.core.Runtime;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
    public Matrix4f view = new Matrix4f(), projection = new Matrix4f();
    public Vector3f position = new Vector3f(2.5f, 2.5f, 0);
    public Quaternionf rotation = new Quaternionf();
    public float near = 0, far = 100, fov = 70;

    public void calculateView(){
        view.identity();
        view.translate(position);
        view.rotate(rotation);
    }

    public void calculateProjection() {
        projection.identity();
        projection.ortho(0, Runtime.display.width / 100, 0, Runtime.display.height / 100, near, far);
    }
}
