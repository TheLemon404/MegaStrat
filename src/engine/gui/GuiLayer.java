package engine.gui;

import engine.core.Globals;
import engine.graphics.GuiRenderer;
import engine.graphics.Shader;
import engine.gui.widgets.Button;

import java.util.ArrayList;

public class GuiLayer {

    private static ArrayList<Window> windows = new ArrayList<>();
    private static Shader shader;

    public static void load(){
        Window window = new Window();

        window.widgets.add(new Button());

        windows.add(window);

        for(Window w : windows){
            w.load();
            for(Widget wi : window.widgets){
                wi.mesh.load();
            }
        }

        shader = Globals.guiShader;
    }

    public static void draw(){
        for(Window window : windows){
            window.rect.calculateMatrix();
            GuiRenderer.submit(shader, window, window.rect);
            for(Widget widget : window.widgets){
                window.rect.calculateMatrix(widget.subPosition, widget.subRotation, widget.subScale);
                GuiRenderer.submit(shader, widget, window.rect);
            }
        }
    }
}
