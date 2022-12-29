package engine.gui;

import engine.graphics.Mesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Widget {
    public float subRotation = 0;
    public Vector2f subPosition = new Vector2f();
    public Vector3f color = new Vector3f(0.6f, 0.6f, 0.6f);

    public Vector2f subScale = new Vector2f(1,1);
    public Mesh mesh = new Mesh();

    public abstract void load();
    public abstract void update();
}
