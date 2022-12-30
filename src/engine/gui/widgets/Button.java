package engine.gui.widgets;

import engine.gui.Widget;
import engine.types.ImageTexture;
import engine.types.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.swing.*;

public class Button extends Widget {

    public Runnable callback;

    public Button(Runnable callback){
        this.callback = callback;
    }

    @Override
    public void onClick() {
        callback.run();
    }

    @Override
    public void load(){
        super.subScale = new Vector2f(0.5f, 0.5f);
        super.subPosition = new Vector2f(-2.2f, 2.2f);
        super.color = new Vector3f(0.5f, 0.8f, 0.7f);
    }

    @Override
    public void update(){

    }
}
