package game.scene;

import engine.events.KeyboardManager;
import engine.structure.Scene;
import game.entities.GridSelect;
import game.entities.Projectile;
import game.units.Tank;
import engine.structure.Terrain;
import game.units.Worker;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Battlefield extends Scene {

    private int i = 2;
    private Projectile projectile;
    private Tank testTank;

    @Override
    public void load() {
        new Terrain(this, new Vector3f(0.4f, 0.8f, 0.6f));

        super.addEntity(new GridSelect());

        testTank = new Tank();
        super.addEntity(testTank);

        //super.addEntity(new Worker());

        projectile = new Projectile(new Vector3f(0,0,1), new Vector3f(0.2f,0.3f,0));
        super.addEntity(projectile);
    }

    @Override
    public void update() {
        if(KeyboardManager.isKeyDown(GLFW_KEY_SPACE) && i < 4){
            Tank tank = new Tank();
            tank.meshInstance.transform.position.x = i;
            tank.meshInstance.transform.position.y = 0;
            tank.meshInstance.linearVelocity.x = -20;
            super.addEntity(tank);
            i += 2;
        }
        if(KeyboardManager.isKeyDown(GLFW_KEY_J)){
            projectile.meshInstance.linearVelocity.y += 0.2f;
            projectile.meshInstance.linearVelocity.x = 0.3f;
        }
    }

    @Override
    public void end() {

    }
}

