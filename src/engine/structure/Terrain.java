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

public class Terrain {
    public Instance instance;
    public Terrain(Scene scene, int size, int ox, int oz) {
        instance = InstanceImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx", Globals.instanceShader);
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                Transform t = new Transform();
                t.position = new Vector3f((ox * 20) + x * 2, 0, (oz * 20) + z * 2);
                Material material = new Material();
                float g = MathTools.clamp(new Random().nextFloat(), -0.1f, 0.2f);
                float b = MathTools.clamp(new Random().nextFloat(), -0.1f, 0.1f);
                material.color = new Vector3f(0.4f, 0.6f + g, 0.4f + b);
                instance.materials.add(material);
                instance.addTransformWithId(t);
            }
        }
        scene.addInstance(instance);
    }
}
