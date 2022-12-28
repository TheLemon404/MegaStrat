package engine.structure;

import engine.core.Globals;
import engine.core.Runtime;
import engine.events.KeyboardManager;
import engine.events.MouseManager;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    public Matrix4f view = new Matrix4f(), projection = new Matrix4f();
    public Vector3f position = new Vector3f(3, 2, -10);
    public Vector3f rotation = new Vector3f();
    public float near = 0, far = 100, fov = 70, zoom = 1f;
    public float speed = -0.011f;

    public Camera(){
        rotation.y = (float)Math.toRadians(45);
        rotation.x = (float)Math.toRadians(45);
    }

    private void update(){
        if(MouseManager.isButtonDown(GLFW_MOUSE_BUTTON_2)){
            Vector2f delta = MouseManager.mouseDelta.mul(speed * zoom);
            position.z += delta.y;
            position.y -= delta.y;
            position.x += delta.x;
        }
        zoom -= MouseManager.scrollDelta / 50;
    }

    public void calculateMatrix(){
        update();

        view.identity();
        view.translate(position);
        view.rotateAffineXYZ(rotation.x, rotation.y, rotation.z);

        projection.identity();
        projection.ortho(0, Runtime.display.width / (150 / Globals.resolution) * zoom, 0, Runtime.display.height / (150 / Globals.resolution) * zoom, near, far);
    }
}
