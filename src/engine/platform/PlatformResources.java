package engine.platform;

import engine.platform.hardware.Monitor;

import java.awt.*;

public class PlatformResources {
    public static Monitor monitor;

    public static void getSystemStats(){
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        monitor = new Monitor(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
    }
}
