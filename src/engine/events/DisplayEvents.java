package engine.events;

import engine.core.Runtime;

public class DisplayEvents {
    public static void onResize(long window, int width, int height) {
        Runtime.display.width = width;
        Runtime.display.height = height;
    }
}
