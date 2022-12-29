package engine.structure;

import engine.core.Runtime;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Sun {
    public float distance = 50;
    public Matrix4f view = new Matrix4f();
    public Vector3f color = new Vector3f(1,0.8f,0.7f);

    public void calculateMatrix(){
        view.identity();
        view.translate(new Vector3f(-Runtime.currentScene.lightDirection.x * distance, -Runtime.currentScene.lightDirection.y * distance, -Runtime.currentScene.lightDirection.z * distance));
        view.lookAt(new Vector3f(-Runtime.currentScene.lightDirection.x * distance, -Runtime.currentScene.lightDirection.y * distance, -Runtime.currentScene.lightDirection.z * distance), Runtime.currentScene.lightDirection, new Vector3f(0,1,0));
    }
}
