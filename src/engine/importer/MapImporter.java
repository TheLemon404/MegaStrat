package engine.importer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import engine.core.Globals;
import engine.graphics.Instance;
import engine.structure.BlankScene;
import engine.structure.Scene;
import engine.types.Transform;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MapImporter {
    public static Scene importMapFromFile(String path) throws IOException {
        Scene importedScene = new BlankScene();

        Object obj = JsonParser.parseReader(new FileReader(path));

        JsonObject root = (JsonObject) obj;

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

        return importedScene;
    }
}
