package game.scene;

import engine.events.KeyboardManager;
import engine.graphics.Instance;
import engine.gui.GuiLayer;
import engine.gui.Window;
import engine.gui.widgets.Button;
import engine.structure.Scene;
import game.entities.GridSelect;
import game.entities.Projectile;
import game.units.Tank;
import game.instances.Terrain;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class Battlefield extends Scene {

    int i = 2;

    @Override
    public void load() {
        new Terrain(this, 10, 0, 0);
        new Terrain(this, 10, 1, 1);
        new Terrain(this, 10, 0, 1);
        new Terrain(this, 10, 1, 0);

        new Terrain(this, 10, 2, 2);
        new Terrain(this, 10, 3, 3);
        new Terrain(this, 10, 2, 3);
        new Terrain(this, 10, 3, 2);

        new Terrain(this, 10, 2, 0);
        new Terrain(this, 10, 3, 1);
        new Terrain(this, 10, 2, 1);
        new Terrain(this, 10, 3, 0);

        new Terrain(this, 10, 0, 2);
        new Terrain(this, 10, 1, 3);
        new Terrain(this, 10, 0, 3);
        new Terrain(this, 10, 1, 2);

        super.addEntity(new GridSelect());

        super.addEntity(new Tank());
        Projectile projectile = new Projectile(new Vector3f(2,0,2), new Vector3f(0.2f,0.1f,0));
        super.addEntity(projectile);
    }

    @Override
    public void update() {
        if(KeyboardManager.isKeyDown(GLFW_KEY_SPACE) && i < 80){
            Tank tank = new Tank();
            tank.meshInstance.transform.position.x = i;
            super.addEntity(tank);
            i += 2;
        }
    }

    @Override
    public void end() {

    }
}
