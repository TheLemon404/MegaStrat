package engine.core;

import engine.events.MouseManager;
import engine.structure.Entity;

public class CommandController {
    public static boolean isItemSelected = false;
    // ^--- Tells if unit is selected
    public static Entity itemSelected;
    // ^--- Last item that was selected

    // Called on first frame
    public void start() {

    }

    // Called every frame
    public void update() {
        // Selects unit if new click, Not Mouse Held
        if(MouseManager.newClick()){
            //currentEntityId = GraphicsRuntime.frameBuffer.sampleId();
        }
    }
    // Called on last/closing frame
    public void end() {

    }
}
