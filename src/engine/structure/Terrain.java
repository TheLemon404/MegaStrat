package engine.structure;

import engine.core.Globals;
import engine.graphics.Instance;
import engine.graphics.Material;
import engine.importer.InstanceImporter;
import engine.structure.Scene;
import engine.types.Transform;
import engine.utils.MathTools;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Terrain {
    public Vector3f color;

    private Scene currentScene;
    public Terrain(Scene scene, Vector3f color) {
        currentScene = scene;
        this.color = color;

        loadCell(0, 0);
        loadCell(1,1);
        loadCell(0,1);
        loadCell(1,0);

        loadCell(2,2);
        loadCell(3,3);
        loadCell(2,3);
        loadCell(3,2);

        loadCell(2, 0);
        loadCell(3,1);
        loadCell(2, 1);
        loadCell(3, 0);

        loadCell(0, 2);
        loadCell(1, 3);
        loadCell(0,3);
        loadCell(1, 2);
    }

    private void loadCell(int ox, int oz){
        Instance instance = InstanceImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx", Globals.instanceShader);
        for (int x = 0; x < 10; x++) {
            for (int z = 0; z < 10; z++) {
                Transform t = new Transform();
                t.position = new Vector3f((ox * 20) + x * 2, 0, (oz * 20) + z * 2);
                Material material = new Material();
                material.color = new Vector3f(color.x, color.y, color.z);
                instance.mesh.material = material;
                instance.addTransformWithId(t);
            }
        }
        currentScene.addInstance(instance);
    }
}
