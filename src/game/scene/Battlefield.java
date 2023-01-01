package game.scene;

import engine.events.KeyboardManager;
import engine.structure.Scene;
import game.entities.GridSelect;
import game.entities.Projectile;
import game.units.Tank;
import engine.structure.Terrain;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Battlefield extends Scene {

    private int i = 2;
    private Projectile projectile;

    @Override
    public void load() {
        new Terrain(this, new Vector3f(0.4f, 0.8f, 0.6f));

        super.addEntity(new GridSelect());

        super.addEntity(new Tank());
        projectile = new Projectile(new Vector3f(2,0,2), new Vector3f(0.02f,0.05f,0));
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
        if(KeyboardManager.isKeyDown(GLFW_KEY_J)){
            projectile.meshInstance.linearVelocity.y = 0.3f;
            projectile.meshInstance.linearVelocity.x = 0.06f;
        }
    }

    @Override
    public void end() {

    }
}
