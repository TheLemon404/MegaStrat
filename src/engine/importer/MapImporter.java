package engine.importer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import engine.core.Globals;
import engine.graphics.Instance;
import engine.structure.BlankScene;
import engine.structure.Scene;
import engine.structure.Terrain;
import engine.types.Transform;
import game.entities.GridSelect;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class MapImporter {
    public static Scene importMapFromFile(String path) throws IOException {
        Scene importedScene = new BlankScene();

        Object obj = JsonParser.parseReader(new FileReader(path));

        JsonObject root = (JsonObject) obj;

        loadInstances(importedScene, root);
        loadEnvironment(importedScene, root);
        loadTerrain(importedScene, root);

        importedScene.addEntity(new GridSelect());

        return importedScene;
    }

    private static void loadTerrain(Scene importedScene, JsonObject root){
        JsonObject ter = (JsonObject) root.get("terrain");

        JsonArray colorArray = ter.get("color").getAsJsonArray();
        Vector3f color = new Vector3f(colorArray.get(0).getAsFloat(), colorArray.get(1).getAsFloat(), colorArray.get(2).getAsFloat());

        new Terrain(importedScene, color);
    }

    private static void loadEnvironment(Scene importedScene, JsonObject root){
        JsonObject env = (JsonObject) root.get("environment");

        Map<String, JsonElement> map = env.asMap();

        Vector3f lightColor;
        Vector3f lightDirection;

        JsonArray colorArray = map.get("light_color").getAsJsonArray();
        lightColor = new Vector3f(colorArray.get(0).getAsFloat(), colorArray.get(1).getAsFloat(), colorArray.get(2).getAsFloat());

        JsonArray directionArray = map.get("light_direction").getAsJsonArray();
        lightDirection = new Vector3f(directionArray.get(0).getAsFloat(), directionArray.get(1).getAsFloat(), directionArray.get(2).getAsFloat());

        importedScene.sun.color = lightColor;
        importedScene.lightDirection = lightDirection;
    }

    private static void loadInstances(Scene importedScene, JsonObject root){
        JsonObject is = (JsonObject) root.get("instances");

        Map<String, JsonElement> map = is.asMap();

        for(String paths : map.keySet()){
            Instance instance = new Instance();
            instance.mesh = MeshImporter.loadMeshFromFile(paths);

            JsonArray positions = map.get(paths).getAsJsonArray();

            for(JsonElement position : positions){
                JsonArray xyz = position.getAsJsonArray();
                Transform transform = new Transform();
                transform.position.x = xyz.get(0).getAsFloat();
                transform.position.y = xyz.get(1).getAsFloat();
                transform.position.z = xyz.get(2).getAsFloat();
                instance.addTransformWithId(transform);
            }

            importedScene.addInstance(instance);
        }
    }
}
