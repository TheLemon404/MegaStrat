package engine.structure;

import engine.core.Globals;
import engine.graphics.Instance;
import engine.graphics.Material;
import engine.importer.InstanceImporter;
import engine.types.Transform;
import org.joml.Vector3f;

public class Terrain {
    public Vector3f color;
    private Scene currentScene;
    public Terrain(Scene scene, Vector3f color) {
        currentScene = scene;
        this.color = color;

        loadTile(0, 0);
        loadTile(1,1);
        loadTile(0,1);
        loadTile(1,0);

        loadTile(2,2);
        loadTile(3,3);
        loadTile(2,3);
        loadTile(3,2);

        loadTile(2, 0);
        loadTile(3,1);
        loadTile(2, 1);
        loadTile(3, 0);

        loadTile(0, 2);
        loadTile(1, 3);
        loadTile(0,3);
        loadTile(1, 2);
    }

    private void loadTile(int ox, int oz){
        Instance instance = InstanceImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx", Globals.instanceShader);
        for (int x = 0; x < 10; x++) {
            for (int z = 0; z < 10; z++) {
                Transform t = new Transform();
                t.position = new Vector3f((ox * 20) + x * 2, 0, (oz * 20) + z * 2);
                Material material = new Material();
                material.color = new Vector3f(color.x, color.y, color.z);
                material.darkColor = new Vector3f(color.x - 0.5f, color.y - 0.3f, color.z - 0.3f);
                instance.mesh.material = material;
                instance.addTransformWithId(t);
            }
        }
        currentScene.addInstance(instance);
    }
}
