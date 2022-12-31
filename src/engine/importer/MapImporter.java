package engine.importer;

import engine.structure.BlackScene;
import engine.structure.Scene;

import java.io.FileReader;

public class MapImporter {
    public static Scene importMapFromFile(String path){
        Scene importedScene = new BlackScene();

        try {
            FileReader reader = new FileReader(path);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return importedScene;
    }
}
