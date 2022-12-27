package engine.structure;

import engine.core.Globals;
import engine.core.Runtime;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
    public Matrix4f view = new Matrix4f(), projection = new Matrix4f();
    public Vector3f position = new Vector3f(2.5f, 2, -10);
    public Vector3f rotation = new Vector3f();
    public float near = 0, far = 100, fov = 70;

    public Camera(){
        rotation.y = (float)Math.toRadians(45);
        rotation.x = (float)Math.toRadians(45);
    }

    private void update(){
    }

    public void calculateView(){
        update();

        view.identity();
        view.translate(position);
        view.rotateAffineXYZ(rotation.x, rotation.y, rotation.z);
    }

    public void calculateProjection() {
        projection.identity();
        projection.ortho(0, Runtime.display.width / (150 / Globals.resolution), 0, Runtime.display.height / (150 / Globals.resolution), near, far);
    }
}
