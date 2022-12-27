package engine.graphics;

import engine.types.ImageTexture;
import org.joml.Vector3f;

public class Material {
    public Vector3f color = new Vector3f(1,0,0);

    public ImageTexture texture = new ImageTexture("src/resources/textures/white.png");
}
