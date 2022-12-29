package engine.gui;

import engine.graphics.Mesh;
import engine.gui.Rect;
import engine.gui.Widget;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Window {
    public Rect rect = new Rect();
    public Mesh mesh = new Mesh();

    public Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);

    public ArrayList<Widget> widgets = new ArrayList<>();

    public void load(){
        mesh.load();
        for(Widget widget : widgets){
            widget.load();
        }
    }
}
