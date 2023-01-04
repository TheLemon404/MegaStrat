package engine.core;

import engine.events.MouseManager;
import engine.structure.Entity;

public class CommandController {
    public static boolean isItemSelected = false;
    // ^--- Tells if unit is selected
    public static Entity itemSelected;
    // ^--- Last item that was selected

    public static int currentTileId;
    // ^--- ID of Tile selected
    public static int currentEntityId;
    // ^--- ID of Entity selected

    // Called on first frame
    public static void start() {

    }

    // Called every frame
    public static void update() {
        // Selects unit if new click, Not Mouse Held
        if(MouseManager.newClick()){
            currentEntityId = GraphicsRuntime.frameBuffer.sampleId();
        }

        // Current Tile ID is set to tile mouse currently is over
        currentTileId = GraphicsRuntime.frameBuffer.sampleTile();
    }
    // Called on last/closing frame
    public static void end() {

    }
}
